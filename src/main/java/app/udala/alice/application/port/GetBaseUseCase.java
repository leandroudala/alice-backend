package app.udala.alice.application.port;

import java.util.List;

import app.udala.alice.infrastructure.delivery.dto.BaseDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.BaseResponse;

public interface GetBaseUseCase {

    List<BaseResponse> getAll();

    BaseDetailsResponse getById(String id);

}
