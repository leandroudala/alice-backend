package app.udala.alice.application.port;

import app.udala.alice.infrastructure.delivery.dto.BaseResponse;
import app.udala.alice.infrastructure.delivery.dto.BaseUpdateRequest;

public interface ManageBaseUseCase {
    BaseResponse updateBase(BaseUpdateRequest update);

    void deleteBase(String id);
}
