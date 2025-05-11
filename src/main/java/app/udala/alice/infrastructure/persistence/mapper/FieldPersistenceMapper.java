package app.udala.alice.infrastructure.persistence.mapper;

import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.persistence.entity.FieldDocument;

public class FieldPersistenceMapper {
    public static Field toDomain(FieldDocument entity) {
        Field model = new Field();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setTag(entity.getTag());
        model.setDescription(entity.getDescription());
        model.setDatabaseId(entity.getDatabaseId());
        model.setDataType(entity.getDataType());
        model.setRequired(entity.getRequired());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeletedAt(entity.getDeletedAt());
        return model;
    }

    public static FieldDocument toEntity(Field model) {
        FieldDocument document = new FieldDocument();
        document.setId(model.getId());
        document.setName(model.getName());
        document.setTag(model.getTag());
        document.setDescription(model.getDescription());
        document.setDataType(model.getDataType());
        document.setRequired(model.getRequired());
        document.setDatabaseId(model.getDatabaseId());
        document.setCreatedAt(model.getCreatedAt());
        document.setUpdatedAt(model.getUpdatedAt());
        document.setDeletedAt(model.getDeletedAt());
        return document;
    }
}
