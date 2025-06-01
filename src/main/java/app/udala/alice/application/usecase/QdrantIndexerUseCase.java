package app.udala.alice.application.usecase;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ListenableFuture;

import app.udala.alice.EmbedStringRequest;
import app.udala.alice.EmbedVectorResponse;
import app.udala.alice.EmbeddingServiceGrpc;
import app.udala.alice.application.port.VectorIndexerUseCase;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections.Distance;
import io.qdrant.client.grpc.Collections.VectorParams;

public class QdrantIndexerUseCase implements VectorIndexerUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(QdrantIndexerUseCase.class);
    private static final Long EMBEDDING_SIZE = 384l;
    
    private final EmbeddingServiceGrpc.EmbeddingServiceBlockingStub embeddingServiceBlockingStub;
    private final QdrantClient vectorRepository;


    public QdrantIndexerUseCase(QdrantClient vectorRepository, EmbeddingServiceGrpc.EmbeddingServiceBlockingStub embeddingServiceBlockingStub) {
        this.vectorRepository = vectorRepository;
        this.embeddingServiceBlockingStub = embeddingServiceBlockingStub;
    }

    @Override
    public List<Float> textToEmbed(String text) {
        EmbedStringRequest request = EmbedStringRequest.newBuilder()
        .setText(text)
        .build();

        EmbedVectorResponse response = embeddingServiceBlockingStub.getEmbedding(request);
        return response.getEmbeddingVectorList();
    }

    @Override
    public void indexDocument(String collection, String document) {
        this.checkVectorCollectionExists(collection);
        EmbedStringRequest request = EmbedStringRequest.newBuilder()
        .setText(document)
        .build();

        EmbedVectorResponse response = embeddingServiceBlockingStub.getEmbedding(request);

        printList(response.getEmbeddingVectorList());
    }

    private static void printList(List<Float> lista){
        LOG.info("Recebido de gRPC");
        lista.stream().forEach(value -> {
            LOG.info("> {}", value);
        });
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

    private void createVectorCollection(String collection) {
        VectorParams vectorParams = VectorParams.newBuilder()
                .setSize(EMBEDDING_SIZE)
                .setDistance(Distance.Cosine)
                .build();

        this.vectorRepository.createCollectionAsync(collection, vectorParams);
    }

}
