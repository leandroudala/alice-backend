package app.udala.alice.application.port;

import java.util.List;

import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;

public interface GetDataBaseUseCase {

    List<DataBaseResponse> getAll();

    DataBaseResponse getById(String id);

}
