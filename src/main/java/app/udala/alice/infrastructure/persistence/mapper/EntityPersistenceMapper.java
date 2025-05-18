package app.udala.alice.infrastructure.persistence.mapper;

import app.udala.alice.domain.entity.Entity;
import app.udala.alice.infrastructure.persistence.entity.EntityDocument;

public class EntityPersistenceMapper {
    public static EntityDocument toDocument(Entity entity) {
        EntityDocument document = new EntityDocument();
        document.setId(entity.getId());
        document.setName(entity.getName());
        document.setDescription(entity.getDescription());
        document.setBaseId(entity.getBaseId());
        document.setCreatedAt(entity.getCreatedAt());
        document.setUpdatedAt(entity.getUpdatedAt());
        document.setDeletedAt(entity.getDeletedAt());
        return document;
    }

    public static Entity toDomain(EntityDocument document) {
        Entity entity = new Entity();
        entity.setId(document.getId());
        entity.setName(document.getName());
        entity.setDescription(document.getDescription());
        entity.setBaseId(document.getBaseId());
        entity.setCreatedAt(document.getCreatedAt());
        entity.setUpdatedAt(document.getUpdatedAt());
        entity.setDeletedAt(document.getDeletedAt());
        return entity;
    }
}
