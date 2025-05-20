package app.udala.alice.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.FormUseCase;
import app.udala.alice.domain.entity.Entity;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.DetailedFormResponse;
import app.udala.alice.infrastructure.delivery.dto.FormResponse;
import app.udala.alice.infrastructure.delivery.mapper.FormDeliveryMapper;
import app.udala.alice.shared.exception.EntityNotFoundException;

public class MongoFormUseCase implements FormUseCase {

    private final EntityRepository entityRepository;
    private final FieldRepository fieldRepository;

    public MongoFormUseCase(EntityRepository entityRepository, FieldRepository fieldRepository) {
        this.entityRepository = entityRepository;
        this.fieldRepository = fieldRepository;
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
    
}
