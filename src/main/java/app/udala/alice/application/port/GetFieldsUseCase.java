package app.udala.alice.application.port;

import java.util.List;

import app.udala.alice.infrastructure.delivery.dto.FieldDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.FieldResponse;

public interface GetFieldsUseCase {
    List<FieldResponse> getFieldsByDatabaseId(String databaseId);

    FieldDetailsResponse findById(String id);
}
