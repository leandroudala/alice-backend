package app.udala.alice.application.port;

import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.FieldCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.FieldUpdateRequest;

public interface ManageFieldsUseCase {
    public Field create(FieldCreateRequest request);
    public void update(String id, FieldUpdateRequest request);
    public void deleteById(String id);
}
