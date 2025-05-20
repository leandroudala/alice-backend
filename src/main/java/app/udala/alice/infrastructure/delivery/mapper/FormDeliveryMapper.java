package app.udala.alice.infrastructure.delivery.mapper;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.domain.entity.Entity;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.DetailedFormResponse;
import app.udala.alice.infrastructure.delivery.dto.FormResponse;

public class FormDeliveryMapper {

    public static FormResponse toResponse(Entity entity) {
        FormResponse response = new FormResponse();
        response.setEntityId(entity.getId());
        response.setName(entity.getName());
        return response;
    }

    public static DetailedFormResponse toDetailedResponse(Entity entity, List<Field> fields) {
        DetailedFormResponse response = new DetailedFormResponse();
        response.setEntityId(entity.getId());
        response.setName(entity.getName());

        response.setFields(
                fields.stream()
                        .map(FieldDeliveryMapper::toDetailedResponse)
                        .collect(Collectors.toList()));

        return response;
    }

}
