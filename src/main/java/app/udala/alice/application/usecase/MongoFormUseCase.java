package app.udala.alice.application.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.google.common.util.concurrent.ListenableFuture;

import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.FormUseCase;
import app.udala.alice.domain.entity.Entity;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.DetailedFormResponse;
import app.udala.alice.infrastructure.delivery.dto.DocumentResponse;
import app.udala.alice.infrastructure.delivery.dto.FormResponse;
import app.udala.alice.infrastructure.delivery.mapper.FormDeliveryMapper;
import app.udala.alice.shared.converter.LocalDateTimeConverter;
import app.udala.alice.shared.exception.EntityNotFoundException;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections.Distance;
import io.qdrant.client.grpc.Collections.VectorParams;

public class MongoFormUseCase implements FormUseCase {

    private final EntityRepository entityRepository;
    private final FieldRepository fieldRepository;
    private final MongoTemplate mongoTemplate;
    private final QdrantClient qudrantClient;

    private static final Long EMBEDDING_SIZE = 384l;

    public MongoFormUseCase(EntityRepository entityRepository, FieldRepository fieldRepository,
            MongoTemplate mongoTemplate, QdrantClient qdrantClient) {
        this.entityRepository = entityRepository;
        this.fieldRepository = fieldRepository;
        this.mongoTemplate = mongoTemplate;
        this.qudrantClient = qdrantClient;
    }

    @Override
    public DetailedFormResponse getForm(String entityId) {
        Entity entity = this.entityRepository.findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException(entityId));

        List<Field> fields = this.fieldRepository.findAllByEntityIdAndDeletedAtIsNull(entityId);
        return FormDeliveryMapper.toDetailedResponse(entity, fields);
    }

    @Override
    public List<FormResponse> getForms() {
        return this.entityRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(FormDeliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public String insertAnswer(String entityId, String answerPayload) {
        Document document = Document.parse(answerPayload);
        document.put("createdAt", LocalDateTimeConverter.convertToString(LocalDateTime.now()));
        return this.saveDynamicDocument(entityId, document);
    }

    private String saveDynamicDocument(String collectionName, Document document) {
        Document saved = this.mongoTemplate.insert(document, collectionName);
        return saved.get("_id").toString();
    }

    @Override
    public List<DocumentResponse> findAllDynamic(String entityId) {
        return this.mongoTemplate.findAll(Document.class, entityId)
                .stream().map(DocumentResponse::new).collect(Collectors.toList());
    }

    @Override
    public void indexAnswers(String entityId) {
        List<DocumentResponse> documents = this.findAllDynamic(entityId);

        // this.qudrantClient.recreateCollectionAsync(entityId, vectorParams);
        this.checkVectorCollectionExists(entityId);
        System.out.println("Documents found: " + documents.size());
    }

    private void checkVectorCollectionExists(String entityId) {
        ListenableFuture<Boolean> collectionExistsAsync = this.qudrantClient.collectionExistsAsync(entityId);
        try {
            boolean collectionExists = collectionExistsAsync.get();
            System.out.println("Collection Exists? " + collectionExists);
            if (!collectionExists) {
                this.createVectorCollection(entityId);
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createVectorCollection(String entityId) {
        VectorParams vectorParams = VectorParams.newBuilder()
        .setSize(EMBEDDING_SIZE)
        .setDistance(Distance.Cosine)
        .build();
        this.qudrantClient.createCollectionAsync(entityId, vectorParams);
    }

}
