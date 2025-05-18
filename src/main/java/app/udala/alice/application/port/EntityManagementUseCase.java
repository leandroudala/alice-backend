package app.udala.alice.application.port;

import java.util.List;

import app.udala.alice.infrastructure.delivery.dto.EntityCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.EntityDetailedResponse;
import app.udala.alice.infrastructure.delivery.dto.EntityResponse;
import app.udala.alice.infrastructure.delivery.dto.EntityUpdateRequest;

public interface EntityManagementUseCase {
    EntityResponse create(EntityCreateRequest request);
    void update(EntityUpdateRequest request);
    void delete(String id);
    EntityDetailedResponse getById(String id);
    List<EntityResponse> getAllByBaseId(String baseId);
}
