package app.udala.alice.application.port;

import app.udala.alice.infrastructure.delivery.dto.DataBaseCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;

public interface CreateDataBaseUseCase {

    DataBaseResponse create(DataBaseCreateRequest request);
}
