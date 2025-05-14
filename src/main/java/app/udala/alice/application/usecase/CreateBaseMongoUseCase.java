package app.udala.alice.application.usecase;

import java.time.LocalDateTime;

import app.udala.alice.application.port.BaseUseCase;
import app.udala.alice.application.port.BaseRepository;
import app.udala.alice.domain.entity.Base;
import app.udala.alice.infrastructure.delivery.dto.BaseCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.BaseResponse;
import app.udala.alice.infrastructure.delivery.mapper.BaseInfrastructureMapper;
import app.udala.alice.infrastructure.persistence.entity.BaseDocument;
import app.udala.alice.infrastructure.persistence.mapper.BasePersistenceMapper;
import app.udala.alice.shared.converter.LocalDateTimeConverter;
import app.udala.alice.shared.exception.BaseDuplicatedNameException;

public class CreateBaseMongoUseCase implements BaseUseCase {

    private final BaseRepository repository;

    public CreateBaseMongoUseCase(BaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public BaseResponse create(BaseCreateRequest request) {
        // Check if the name is already used
        this.checkForDuplicated(request.getName());

        // Create the document
        String createdAt = LocalDateTimeConverter.convertToString(LocalDateTime.now());
        BaseDocument document = new BaseDocument(request.getName(), request.getDescription(),
                createdAt);

        // Save the document
        BaseDocument saved = this.repository.insert(document);
        Base domain = BasePersistenceMapper.toDomain(saved);
        return BaseInfrastructureMapper.toResponse(domain);
    }

    private void checkForDuplicated(String name) {
        // check if the name is already used
        this.repository.findByName(name).ifPresent(document -> {
            throw new BaseDuplicatedNameException(name);
        });
    }

}
