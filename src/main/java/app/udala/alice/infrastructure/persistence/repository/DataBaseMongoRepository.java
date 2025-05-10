package app.udala.alice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

import app.udala.alice.application.port.DataBaseRepository;
import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;

public interface DataBaseMongoRepository extends DataBaseRepository, MongoRepository<DataBaseDocument, String> {
    @Query("{ 'name' : ?0, 'deletedAt' : null }")
    Optional<DataBaseDocument> findByName(@NonNull String name);

    @NonNull List<DataBaseDocument> findAll();

    @NonNull Optional<DataBaseDocument> findById(@NonNull String id);
}
