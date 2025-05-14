package app.udala.alice.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.application.port.BaseRepository;
import app.udala.alice.application.port.GetBaseUseCase;
import app.udala.alice.domain.entity.Base;
import app.udala.alice.infrastructure.delivery.dto.BaseDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.BaseResponse;
import app.udala.alice.infrastructure.delivery.mapper.BaseInfrastructureMapper;
import app.udala.alice.infrastructure.persistence.entity.BaseDocument;
import app.udala.alice.infrastructure.persistence.mapper.BasePersistenceMapper;
import app.udala.alice.shared.converter.LocalDateTimeConverter;
import app.udala.alice.shared.exception.BaseNotFoundException;

public class GetBaseMongoUseCase implements GetBaseUseCase {

    private final BaseRepository baseRepository;

    public GetBaseMongoUseCase(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public List<BaseResponse> getAll() {
        return baseRepository.findAllByDeletedAtIsNull().stream()
                .map(BasePersistenceMapper::toDomain)
                .map(BaseInfrastructureMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override

    public BaseDetailsResponse getById(String id) {
        BaseDocument document = baseRepository.findById(id)
                .orElseThrow(() -> new BaseNotFoundException(id));

        Base base = BasePersistenceMapper.toDomain(document);
        return toDetailsResponse(base);
    }

    private static BaseDetailsResponse toDetailsResponse(Base base) {
        return new BaseDetailsResponse(
                base.getId(),
                base.getName(),
                base.getDescription(),
                LocalDateTimeConverter.convertToString(base.getCreatedAt()),
                LocalDateTimeConverter.convertToString(base.getUpdatedAt()),
                LocalDateTimeConverter.convertToString(base.getDeletedAt()));
    }

}
