package app.udala.alice.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.GetFieldsUseCase;
import app.udala.alice.infrastructure.delivery.dto.FieldResponse;
import app.udala.alice.infrastructure.delivery.mapper.FieldDeliveryMapper;

public class GetFieldsMongoUseCase implements GetFieldsUseCase {
    private final FieldRepository repository;

    public GetFieldsMongoUseCase(FieldRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FieldResponse> getFieldsById(String databaseId) {
        return this.repository.getFieldsByDataBaseId(databaseId).stream()
                .map(FieldDeliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

}
