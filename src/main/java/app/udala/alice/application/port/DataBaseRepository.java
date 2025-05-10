package app.udala.alice.application.port;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;

public interface DataBaseRepository {
    Optional<DataBaseDocument> findByName(@NonNull String name);

    Optional<DataBaseDocument> findById(@NonNull String id);

    List<DataBaseDocument> findAll();

    List<DataBaseDocument> findAllByDeletedAtIsNull();

    DataBaseDocument insert(DataBaseDocument dataBase);

    DataBaseDocument save(DataBaseDocument dataBase);

    void delete(DataBaseDocument dataBase);
}
