package app.udala.alice.application.port;

import java.util.List;
import java.util.Optional;

import app.udala.alice.domain.entity.Field;

public interface FieldRepository {
    List<Field> getFieldsByBaseId(String baseId);

    Field insert(Field field);

    Optional<Field> findById(String id);

    Optional<Field> findByTagAndDeletedAtIsNull(String tag);

    Field save(Field document);

    void delete(Field model);
}
