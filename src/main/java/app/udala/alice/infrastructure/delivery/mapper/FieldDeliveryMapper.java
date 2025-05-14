package app.udala.alice.infrastructure.delivery.mapper;

import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.FieldCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.FieldDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.FieldResponse;
import app.udala.alice.shared.converter.LocalDateTimeConverter;

public class FieldDeliveryMapper {
    public static FieldResponse toResponse(Field model) {
        FieldResponse response = new FieldResponse();
        response.setId(model.getId());
        response.setName(model.getName());
        response.setTag(model.getTag());
        response.setDescription(model.getDescription());
        response.setDataType(model.getDataType());
        return response;
    }

    public static Field toModel(FieldCreateRequest request) {
        Field model = new Field();
        model.setName(request.getName());
        model.setTag(request.getTag());
        model.setDescription(request.getDescription());
        model.setDataType(request.getDataType());
        model.setBaseId(request.getBaseId());
        return model;
    }

    public static FieldDetailsResponse toDetailedResponse(Field model) {
        FieldDetailsResponse response = new FieldDetailsResponse();
        response.setId(model.getId());
        response.setBaseId(model.getBaseId());
        response.setDataType(model.getDataType());
        response.setDescription(model.getDescription());
        response.setName(model.getName());
        response.setRequired(model.getRequired());
        response.setTag(model.getTag());
        response.setCreatedAt(LocalDateTimeConverter.convertToString(model.getCreatedAt()));
        response.setUpdatedAt(LocalDateTimeConverter.convertToString(model.getUpdatedAt()));
        response.setDeletedAt(LocalDateTimeConverter.convertToString(model.getDeletedAt()));
        return response;
    }
}
