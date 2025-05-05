package app.udala.alice.application.port;

import java.util.List;
import java.util.Optional;

import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;

public interface DataBaseRepository {
    Optional<DataBaseDocument> findByName(String name);
    Optional<DataBaseDocument> findById(String id);
    List<DataBaseDocument> findAll();
    DataBaseDocument insert(DataBaseDocument dataBase);
}
