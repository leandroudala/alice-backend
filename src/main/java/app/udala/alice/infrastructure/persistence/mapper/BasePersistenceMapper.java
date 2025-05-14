package app.udala.alice.infrastructure.persistence.mapper;

import java.time.LocalDateTime;

import app.udala.alice.domain.entity.Base;
import app.udala.alice.infrastructure.persistence.entity.BaseDocument;
import app.udala.alice.shared.converter.LocalDateTimeConverter;

public class BasePersistenceMapper {
    public static Base toDomain(BaseDocument entity) {
        LocalDateTime createdAt = LocalDateTimeConverter.convertToLocalDateTime(entity.getCreatedAt());
        LocalDateTime updatedAt = LocalDateTimeConverter.convertToLocalDateTime(entity.getUpdatedAt());
        LocalDateTime deletedAt = LocalDateTimeConverter.convertToLocalDateTime(entity.getDeletedAt());

        return new Base(entity.getId(), entity.getName(), entity.getDescription(), createdAt, updatedAt, deletedAt);
    }

    public static BaseDocument toEntity(Base base) {
        String createdAt = LocalDateTimeConverter.convertToString(base.getCreatedAt());
        String updatedAt = LocalDateTimeConverter.convertToString(base.getUpdatedAt());

        String deletedAt = LocalDateTimeConverter.convertToString(base.getDeletedAt());
        return new BaseDocument(base.getId(), base.getName(), base.getDescription(),
                createdAt, updatedAt, deletedAt);
    }

}
