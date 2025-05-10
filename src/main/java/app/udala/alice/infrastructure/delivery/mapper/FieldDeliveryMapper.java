package app.udala.alice.infrastructure.delivery.mapper;

import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.FieldResponse;

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
}
