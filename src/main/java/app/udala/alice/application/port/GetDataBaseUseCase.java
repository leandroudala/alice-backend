package app.udala.alice.application.port;

import java.util.List;

import app.udala.alice.infrastructure.delivery.dto.DataBaseDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;

public interface GetDataBaseUseCase {

    List<DataBaseResponse> getAll();

    DataBaseDetailsResponse getById(String id);

}
