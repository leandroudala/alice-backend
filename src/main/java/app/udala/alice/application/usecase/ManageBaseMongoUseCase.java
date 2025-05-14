package app.udala.alice.application.usecase;

import java.time.LocalDateTime;

import app.udala.alice.application.port.BaseRepository;
import app.udala.alice.application.port.ManageBaseUseCase;
import app.udala.alice.domain.entity.Base;
import app.udala.alice.infrastructure.delivery.dto.BaseResponse;
import app.udala.alice.infrastructure.delivery.dto.BaseUpdateRequest;
import app.udala.alice.infrastructure.delivery.mapper.BaseInfrastructureMapper;
import app.udala.alice.infrastructure.persistence.entity.BaseDocument;
import app.udala.alice.infrastructure.persistence.mapper.BasePersistenceMapper;
import app.udala.alice.shared.converter.LocalDateTimeConverter;
import app.udala.alice.shared.exception.BaseNotFoundException;

public class ManageBaseMongoUseCase implements ManageBaseUseCase {

    private final BaseRepository repository;

    public ManageBaseMongoUseCase(BaseRepository repository) {
        this.repository = repository;
    }

    private BaseDocument findById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new BaseNotFoundException(id));
    }

    @Override
    public BaseResponse updateBase(BaseUpdateRequest request) {
        // find the document by id
        String id = request.getId();
        BaseDocument document = this.findById(id);

        // Check if the name is already used if the user changes it
        if (!request.getName().equalsIgnoreCase(document.getName())) {
            this.checkForDuplicated(request.getName());
        }

        // update the document
        document.setName(request.getName());
        document.setDescription(request.getDescription());
        document.setUpdatedAt(LocalDateTimeConverter.convertToString(LocalDateTime.now()));

        BaseDocument updatedDocument = this.repository.save(document);

        // convert the document to domain
        Base domain = BasePersistenceMapper.toDomain(updatedDocument);
        return BaseInfrastructureMapper.toResponse(domain);
    }

    private void checkForDuplicated(String name) {
        // check if the name is already used
        this.repository.findByName(name).ifPresent(document -> {
            throw new IllegalArgumentException("The name " + name + " is already used");
        });
    }

    @Override
    public void deleteBase(String id) {
        // check if the document exists
        BaseDocument document = this.findById(id);
        document.setDeletedAt(LocalDateTimeConverter.convertToString(LocalDateTime.now()));

        // soft delete
        this.repository.save(document);
    }

}
