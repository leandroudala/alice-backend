package app.udala.alice.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.GetFieldsUseCase;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.FieldDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.FieldResponse;
import app.udala.alice.infrastructure.delivery.mapper.FieldDeliveryMapper;
import app.udala.alice.shared.exception.FieldNotFoundException;

public class GetFieldsMongoUseCase implements GetFieldsUseCase {
    private final FieldRepository repository;

    public GetFieldsMongoUseCase(FieldRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FieldResponse> getFieldsByDatabaseId(String databaseId) {
        return this.repository.getFieldsByDataBaseId(databaseId).stream()
                .map(FieldDeliveryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FieldDetailsResponse findById(String id) {
        Field model = this.repository.findById(id)
        .orElseThrow(() -> new FieldNotFoundException(id));
        
        return FieldDeliveryMapper.toDetailedResponse(model);
    }

}
