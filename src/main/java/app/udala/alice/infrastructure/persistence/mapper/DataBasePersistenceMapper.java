package app.udala.alice.infrastructure.persistence.mapper;

import java.time.LocalDateTime;

import app.udala.alice.domain.entity.DataBase;
import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;
import app.udala.alice.shared.converter.LocalDateTimeConverter;

public class DataBasePersistenceMapper {
    public static DataBase toDomain(DataBaseDocument entity) {
        LocalDateTime createdAt = LocalDateTimeConverter.convertToLocalDateTime(entity.getCreatedAt());
        LocalDateTime updatedAt = LocalDateTimeConverter.convertToLocalDateTime(entity.getUpdatedAt());
        LocalDateTime deletedAt = LocalDateTimeConverter.convertToLocalDateTime(entity.getDeletedAt());

        return new DataBase(entity.getId(), entity.getName(), entity.getDescription(), createdAt, updatedAt, deletedAt);
    }

    public static DataBaseDocument toEntity(DataBase dataBase) {
        String createdAt = LocalDateTimeConverter.convertToString(dataBase.getCreatedAt());
        String updatedAt = LocalDateTimeConverter.convertToString(dataBase.getUpdatedAt());

        String deletedAt = LocalDateTimeConverter.convertToString(dataBase.getDeletedAt());
        return new DataBaseDocument(dataBase.getId(), dataBase.getName(), dataBase.getDescription(),
                createdAt, updatedAt, deletedAt);
    }

}
