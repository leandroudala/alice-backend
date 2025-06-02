package app.udala.alice.application.usecase;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.util.concurrent.ListenableFuture;

import app.udala.alice.EmbedStringRequest;
import app.udala.alice.EmbedVectorResponse;
import app.udala.alice.EmbeddingServiceGrpc.EmbeddingServiceBlockingStub;
import app.udala.alice.GenerativeServiceGrpc.GenerativeServiceBlockingStub;
import app.udala.alice.application.port.VectorIndexerUseCase;
import app.udala.alice.shared.exception.MongoDocumentNotFoundException;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QueryFactory;
import io.qdrant.client.ValueFactory;
import io.qdrant.client.grpc.Collections.Distance;
import io.qdrant.client.grpc.Collections.VectorParams;
import io.qdrant.client.grpc.JsonWithInt.Value;
import io.qdrant.client.grpc.Points.PointId;
import io.qdrant.client.grpc.Points.PointStruct;
import io.qdrant.client.grpc.Points.QueryPoints;
import io.qdrant.client.grpc.Points.ScoredPoint;
import io.qdrant.client.grpc.Points.UpsertPoints;
import io.qdrant.client.grpc.Points.Vector;
import io.qdrant.client.grpc.Points.Vectors;
import io.qdrant.client.grpc.Points.Vectors.Builder;
import io.qdrant.client.grpc.Points.WithPayloadSelector;

public class QdrantIndexerUseCase implements VectorIndexerUseCase {

    // embedding size for `multilingual-e5-base-q4_k_m` model is 768
    private static final Long EMBEDDING_SIZE = 768l;

    private static final Logger LOG = LoggerFactory.getLogger(QdrantIndexerUseCase.class);

    private final EmbeddingServiceBlockingStub embeddingService;
    private final GenerativeServiceBlockingStub generativeService;
    private final QdrantClient vectorRepository;
    private final MongoTemplate mongoRepository;
    private final static Set<String> IGNORED_PROPERTIES = Set.of(
            "createdAt",
            "updatedAt",
            "deletedAt");

    public QdrantIndexerUseCase(QdrantClient vectorRepository,
            EmbeddingServiceBlockingStub embeddingServiceBlockingStub,
            GenerativeServiceBlockingStub generativeService,
            MongoTemplate mongoRepository) {
        this.vectorRepository = vectorRepository;
        this.embeddingService = embeddingServiceBlockingStub;
        this.generativeService = generativeService;
        this.mongoRepository = mongoRepository;
    }

    @Override
    public List<Float> textToEmbed(String text) {
        if (text == null) {
            throw new RuntimeException("Text should not be null");
        }
        EmbedStringRequest request = EmbedStringRequest.newBuilder()
                .setText(text)
                .build();

        EmbedVectorResponse response = embeddingService.getEmbedding(request);

        return response.getEmbeddingVectorList();
    }

    @Override
    public void indexDocument(String documentId, String collectionName) {
        Query query = new Query(Criteria.where("_id").is(documentId));
        Document document = this.mongoRepository.findOne(query, Document.class, collectionName);

        LOG.info("Document: {}", document);
        if (document == null) {
            throw new MongoDocumentNotFoundException(documentId);
        }

        String text = documentToString(document);
        LOG.info("Document string:\n{}", text);

        this.checkVectorCollectionExists(collectionName);

        List<Float> embedding = this.textToEmbed(text);

        this.saveEmbeddings(documentId, collectionName, document, embedding);
    }

    private void saveEmbeddings(String documentId, String collectionName, Document document, List<Float> embeddings) {
        UUID uuid = UUID.nameUUIDFromBytes(documentId.getBytes());

        PointId pointId = PointId.newBuilder().setUuid(uuid.toString()).build();
        Vector vector = Vector.newBuilder().addAllData(embeddings).build();
        Builder vectorsBuilder = Vectors.newBuilder().setVector(vector);

        PointStruct.Builder pointStructBuilder = PointStruct.newBuilder()
                .setId(pointId)
                .setVectors(vectorsBuilder);

        for (String key : document.keySet()) {
            if (key.charAt(0) == '_' || IGNORED_PROPERTIES.contains(key))
                continue;
            Value value = ValueFactory.value(document.get(key).toString());
            pointStructBuilder.putPayload(key, value);
        }

        UpsertPoints upsertPoints = UpsertPoints.newBuilder()
                .setCollectionName(collectionName)
                .addPoints(pointStructBuilder)
                .build();

        this.vectorRepository.upsertAsync(upsertPoints);
    }

    private void checkVectorCollectionExists(String collection) {
        ListenableFuture<Boolean> collectionExistsAsync = this.vectorRepository.collectionExistsAsync(collection);
        try {
            boolean collectionExists = collectionExistsAsync.get();
            System.out.println("Collection Exists? " + collectionExists);
            if (!collectionExists) {
                this.createVectorCollection(collection);
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String documentToString(Document document) {
        StringBuilder builder = new StringBuilder("passage: ");
        for (String key : document.keySet()) {
            if (key.charAt(0) == '_') {
                // ignore property if it starts with underscode
                continue;
            }

            if (IGNORED_PROPERTIES.contains(key)) {
                // ignore some properties generated automatically
                continue;
            }

            String value = document.get(key).toString();
            builder.append(key).append(": ").append(value).append("\n");
        }

        return builder.toString().stripTrailing();
    }

    private void createVectorCollection(String collection) {
        VectorParams vectorParams = VectorParams.newBuilder()
                .setSize(EMBEDDING_SIZE)
                .setDistance(Distance.Cosine)
                .build();

        this.vectorRepository.createCollectionAsync(collection, vectorParams);
    }

    @Override
    public String search(String prompt) {
        LOG.info("user prompt: {}", prompt);
        List<Float> embedPrompt = this.textToEmbed(prompt);
        QueryPoints search = QueryPoints.newBuilder()
                .setCollectionName("6828de5f431c60717509d88f")
                .setWithPayload(WithPayloadSelector.newBuilder().setEnable(true).build())
                .setLimit(1l)
                .setQuery(QueryFactory.nearest(embedPrompt))
                .build();

        try {
            List<ScoredPoint> list = this.vectorRepository.queryAsync(search).get();
            StringBuilder context = new StringBuilder();
            for (ScoredPoint point : list) {
                LOG.info("> {}: {}", point.getScore(), point.getPayloadCount());

                if (point.getPayloadCount() > 0) {
                    for (String payloadKey : point.getPayloadMap().keySet()) {
                        String value = point.getPayloadOrDefault(payloadKey, null).getStringValue();
                        LOG.info("  - {}: {}", payloadKey, value);
                        context.append(payloadKey).append(": ").append(value).append("\n");
                    }
                }
            }

            context.append("\nBaseado EXCLUSIVAMENTE NO CONTEXTO acima, responda: ")
                    .append(prompt);

            return this.generateTextWithContext(context.toString().stripTrailing());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private String generateTextWithContext(String context) {
        EmbedStringRequest request = EmbedStringRequest.newBuilder()
                .setText(context)
                .build();

        StringBuilder sb = new StringBuilder();
        this.generativeService.generativeText(request).forEachRemaining(response -> {
            String text = response.getText();
            System.out.print(text);
            sb.append(text);
        });

        String response = sb.toString();
        LOG.info("LLM Response: {}", response);
        return response;
    }

}
