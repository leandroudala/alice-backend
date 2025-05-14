package app.udala.alice.infrastructure.delivery.mapper;

import app.udala.alice.domain.entity.Base;
import app.udala.alice.infrastructure.delivery.dto.BaseResponse;

public class BaseInfrastructureMapper {

    public static BaseResponse toResponse(Base base) {
        return new BaseResponse(base.getId(), base.getName());
    }

}
