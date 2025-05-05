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
import app.udala.alice.shared.converter.LocalDateTimeConverter;
import app.udala.alice.shared.exception.DataBaseDuplicatedNameException;

public class CreateDataBaseMongoUseCase implements CreateDataBaseUseCase {

    private final DataBaseRepository repository;

    public CreateDataBaseMongoUseCase(DataBaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataBaseResponse create(DataBaseCreateRequest request) {
        // Check if the name is already used
        this.checkForDuplicated(request.getName());

        // Create the document
        String createdAt = LocalDateTimeConverter.convertToString(LocalDateTime.now());
        DataBaseDocument document = new DataBaseDocument(request.getName(), request.getDescription(),
                createdAt);

        // Save the document
        DataBaseDocument saved = this.repository.insert(document);
        DataBase domain = DataBasePersistenceMapper.toDomain(saved);
        return DataBaseInfrastructureMapper.toResponse(domain);
    }

    private void checkForDuplicated(String name) {
        // check if the name is already used
        this.repository.findByName(name).ifPresent(document -> {
            throw new DataBaseDuplicatedNameException(name);
        });
    }

}
