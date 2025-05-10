package app.udala.alice.infrastructure.persistence.mapper;

import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.persistence.entity.FieldDocument;
import app.udala.alice.shared.converter.LocalDateTimeConverter;

public class FieldPersistenceMapper {
    public static Field toDomain(FieldDocument field) {
        Field model = new Field();
        model.setId(field.getId());
        model.setName(field.getName());
        model.setTag(field.getTag());
        model.setDescription(field.getDescription());
        model.setDataType(field.getDataType());
        model.setCreatedAt(LocalDateTimeConverter.convertToLocalDateTime(field.getCreatedAt()));
        model.setUpdatedAt(LocalDateTimeConverter.convertToLocalDateTime(field.getUpdatedAt()));
        model.setDeletedAt(LocalDateTimeConverter.convertToLocalDateTime(field.getDeletedAt()));
        return model;
    }
}
