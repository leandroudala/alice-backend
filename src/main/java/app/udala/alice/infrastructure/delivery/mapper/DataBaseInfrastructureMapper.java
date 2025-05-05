package app.udala.alice.infrastructure.delivery.mapper;

import app.udala.alice.domain.entity.DataBase;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;

public class DataBaseInfrastructureMapper {

    public static DataBaseResponse toResponse(DataBase dataBase) {
        return new DataBaseResponse(dataBase.getId(), dataBase.getName());
    }

}
