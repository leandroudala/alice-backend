package app.udala.alice.application.usecase;

import java.time.LocalDateTime;

import app.udala.alice.application.port.CreateDataBaseUseCase;
import app.udala.alice.application.port.DataBaseRepository;
import app.udala.alice.domain.entity.DataBase;
import app.udala.alice.infrastructure.delivery.dto.DataBaseCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;
import app.udala.alice.infrastructure.delivery.mapper.DataBaseInfrastructureMapper;
import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;
import app.udala.alice.infrastructure.persistence.mapper.DataBasePersistenceMapper;

public class CreateDataBaseMongo implements CreateDataBaseUseCase {

    private final DataBaseRepository repository;

    public CreateDataBaseMongo(DataBaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataBaseResponse create(DataBaseCreateRequest request) {
        DataBase model = new DataBase();
        model.setName(request.getName());
        model.setDescription(request.getDescription());
        model.setCreatedAt(LocalDateTime.now());

        DataBaseDocument saved = this.repository.insert(DataBasePersistenceMapper.toEntity(model));
        DataBase domain = DataBasePersistenceMapper.toDomain(saved);
        return DataBaseInfrastructureMapper.toResponse(domain);
    }

}
