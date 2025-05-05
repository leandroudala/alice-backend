package app.udala.alice.application.usecase;

import java.time.LocalDateTime;

import app.udala.alice.application.port.DataBaseRepository;
import app.udala.alice.application.port.ManageDataBaseUseCase;
import app.udala.alice.domain.entity.DataBase;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;
import app.udala.alice.infrastructure.delivery.dto.DataBaseUpdateRequest;
import app.udala.alice.infrastructure.delivery.mapper.DataBaseInfrastructureMapper;
import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;
import app.udala.alice.infrastructure.persistence.mapper.DataBasePersistenceMapper;
import app.udala.alice.shared.converter.LocalDateTimeConverter;
import app.udala.alice.shared.exception.DataBaseNotFoundException;

public class ManageDataBaseMongoUseCase implements ManageDataBaseUseCase {

    private final DataBaseRepository repository;

    public ManageDataBaseMongoUseCase(DataBaseRepository repository) {
        this.repository = repository;
    }

    private DataBaseDocument findById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new DataBaseNotFoundException(id));
    }

    @Override
    public DataBaseResponse updateDataBase(DataBaseUpdateRequest request) {
        // find the document by id
        String id = request.getId();
        DataBaseDocument document = this.findById(id);

        // Check if the name is already used if the user changes it
        if (!request.getName().equalsIgnoreCase(document.getName())) {
            this.checkForDuplicated(request.getName());
        }

        // update the document
        document.setName(request.getName());
        document.setDescription(request.getDescription());
        document.setUpdatedAt(LocalDateTimeConverter.convertToString(LocalDateTime.now()));

        DataBaseDocument updatedDocument = this.repository.save(document);

        // convert the document to domain
        DataBase domain = DataBasePersistenceMapper.toDomain(updatedDocument);
        return DataBaseInfrastructureMapper.toResponse(domain);
    }

    private void checkForDuplicated(String name) {
        // check if the name is already used
        this.repository.findByName(name).ifPresent(document -> {
            throw new IllegalArgumentException("The name " + name + " is already used");
        });
    }

    @Override
    public void deleteDataBase(String id) {
        // check if the document exists
        DataBaseDocument document = this.findById(id);
        document.setDeletedAt(LocalDateTimeConverter.convertToString(LocalDateTime.now()));

        // soft delete
        this.repository.save(document);
    }

}
