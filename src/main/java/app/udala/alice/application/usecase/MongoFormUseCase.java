package app.udala.alice.application.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;

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

public class MongoFormUseCase implements FormUseCase {

    private final EntityRepository entityRepository;
    private final FieldRepository fieldRepository;
    private final MongoTemplate mongoTemplate;

    public MongoFormUseCase(EntityRepository entityRepository, FieldRepository fieldRepository,
            MongoTemplate mongoTemplate) {
        this.entityRepository = entityRepository;
        this.fieldRepository = fieldRepository;
        this.mongoTemplate = mongoTemplate;
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
    public String insert(String entityId, String payload) {
        Document document = Document.parse(payload);
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

}
