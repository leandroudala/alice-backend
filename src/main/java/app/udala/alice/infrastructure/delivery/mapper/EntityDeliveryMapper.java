package app.udala.alice.infrastructure.delivery.mapper;

import app.udala.alice.domain.entity.Entity;
import app.udala.alice.infrastructure.delivery.dto.EntityCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.EntityDetailedResponse;
import app.udala.alice.infrastructure.delivery.dto.EntityResponse;
import app.udala.alice.shared.converter.LocalDateTimeConverter;

public class EntityDeliveryMapper {

    public static EntityDetailedResponse toDetailedResponse(Entity entity) {
        EntityDetailedResponse response = new EntityDetailedResponse();
        response.setId(entity.getBaseId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setCreatedAt(LocalDateTimeConverter.convertToString(entity.getCreatedAt()));
        response.setUpdatedAt(LocalDateTimeConverter.convertToString(entity.getUpdatedAt()));
        response.setDeletedAt(LocalDateTimeConverter.convertToString(entity.getDeletedAt()));
        response.setBaseId(entity.getBaseId());
        return response;
    }

    public static EntityResponse toResponse(Entity entity) {
        EntityResponse response = new EntityResponse();
        response.setId(entity.getBaseId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }

    public static Entity toDomain(EntityCreateRequest request) {
        Entity domain = new Entity();
        domain.setName(request.getName());
        domain.setDescription(request.getDescription());
        domain.setBaseId(request.getBaseId());
        return domain;
    }

}
