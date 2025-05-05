package app.udala.alice.application.port;

import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;
import app.udala.alice.infrastructure.delivery.dto.DataBaseUpdateRequest;

public interface ManageDataBaseUseCase {
    DataBaseResponse updateDataBase(DataBaseUpdateRequest update);

    void deleteDataBase(String id);
}
