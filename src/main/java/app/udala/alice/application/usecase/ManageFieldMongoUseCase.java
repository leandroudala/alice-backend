package app.udala.alice.application.usecase;

import java.time.LocalDateTime;
import java.util.Optional;

import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.ManageFieldUseCase;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.FieldCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.FieldUpdateRequest;
import app.udala.alice.infrastructure.delivery.mapper.FieldDeliveryMapper;
import app.udala.alice.shared.exception.EntityNotFoundException;
import app.udala.alice.shared.exception.FieldDuplicatedTagException;
import app.udala.alice.shared.exception.FieldNotFoundException;

public class ManageFieldMongoUseCase implements ManageFieldUseCase {

    private final FieldRepository repository;
    private final EntityRepository entityRepository;

    public ManageFieldMongoUseCase(FieldRepository repository, EntityRepository entityRepository) {
        this.repository = repository;
        this.entityRepository = entityRepository;
    }

    @Override
    public Field create(FieldCreateRequest request) {
        Field field = FieldDeliveryMapper.toModel(request);
        this.checkIfEntityExists(field.getEntityId());
        field.setCreatedAt(LocalDateTime.now());
        return this.repository.insert(field);
    }

    private void checkIfEntityExists(String entityId) {
        if (entityId == null || entityId.length() == 0) {
            throw new EntityNotFoundException("null");
        }

        this.entityRepository.findById(entityId)
                .orElseThrow(() -> new EntityNotFoundException(entityId));
    }

    @Override
    public void update(FieldUpdateRequest request) {
        String id = request.getId();
        Field model = this.repository.findById(id)
        .orElseThrow(() -> new FieldNotFoundException(id));

        if (!model.getTag().equalsIgnoreCase(request.getTag())) {
            this.checkIfTagExists(request.getTag());
        }

        model.setName(request.getName());
        model.setDataType(request.getDataType());
        model.setTag(request.getTag());
        model.setDescription(request.getDescription());
        model.setRequired(request.getRequired());

        this.repository.save(model);
    }

    private void checkIfTagExists(String tag) {
        Optional<Field> found = this.repository.findByTagAndDeletedAtIsNull(tag);
        if (found.isPresent()) {
            throw new FieldDuplicatedTagException(tag);
        }
    }

    @Override
    public void deleteById(String id) {
        Field model = this.repository.findById(id)
        .orElseThrow(() -> new FieldNotFoundException(id));

        this.repository.delete(model);
    }

}
