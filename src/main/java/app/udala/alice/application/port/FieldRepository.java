package app.udala.alice.application.port;

import java.util.List;

import app.udala.alice.domain.entity.Field;

public interface FieldRepository {
    List<Field> getFieldsByDataBaseId(String databaseId);
}
