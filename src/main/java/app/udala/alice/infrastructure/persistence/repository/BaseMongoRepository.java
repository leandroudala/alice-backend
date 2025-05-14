package app.udala.alice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;

import app.udala.alice.application.port.BaseRepository;
import app.udala.alice.infrastructure.persistence.entity.BaseDocument;

public interface BaseMongoRepository extends BaseRepository, MongoRepository<BaseDocument, String> {
    @Query("{ 'name' : ?0, 'deletedAt' : null }")
    Optional<BaseDocument> findByName(@NonNull String name);

    @NonNull List<BaseDocument> findAll();

    @NonNull Optional<BaseDocument> findById(@NonNull String id);
}
