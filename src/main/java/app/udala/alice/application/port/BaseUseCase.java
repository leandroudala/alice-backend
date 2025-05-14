package app.udala.alice.application.port;

import app.udala.alice.infrastructure.delivery.dto.BaseCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.BaseResponse;

public interface BaseUseCase {

    BaseResponse create(BaseCreateRequest request);
}
