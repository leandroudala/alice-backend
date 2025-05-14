package app.udala.alice.application.usecase;

import java.time.LocalDateTime;
import java.util.Optional;

import app.udala.alice.application.port.BaseRepository;
import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.ManageFieldUseCase;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.FieldCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.FieldUpdateRequest;
import app.udala.alice.infrastructure.delivery.mapper.FieldDeliveryMapper;
import app.udala.alice.shared.exception.BaseNotFoundException;
import app.udala.alice.shared.exception.FieldDuplicatedTagException;
import app.udala.alice.shared.exception.FieldNotFoundException;

public class ManageFieldMongoUseCase implements ManageFieldUseCase {

    private final FieldRepository repository;
    private final BaseRepository baseRepository;

    public ManageFieldMongoUseCase(FieldRepository repository, BaseRepository baseRepository) {
        this.repository = repository;
        this.baseRepository = baseRepository;
    }

    @Override
    public Field create(FieldCreateRequest request) {
        Field field = FieldDeliveryMapper.toModel(request);
        this.checkIfBaseExists(field.getBaseId());
        field.setCreatedAt(LocalDateTime.now());
        return this.repository.insert(field);
    }

    private void checkIfBaseExists(String baseId) {
        if (baseId == null || baseId.length() == 0) {
            throw new BaseNotFoundException("null");
        }

        this.baseRepository.findById(baseId)
                .orElseThrow(() -> new BaseNotFoundException(baseId));
    }

    @Override
    public void update(String id, FieldUpdateRequest request) {
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
