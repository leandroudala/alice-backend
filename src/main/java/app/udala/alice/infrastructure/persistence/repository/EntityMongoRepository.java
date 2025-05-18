package app.udala.alice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.udala.alice.infrastructure.persistence.entity.EntityDocument;

public interface EntityMongoRepository extends MongoRepository<EntityDocument, String> {
    EntityDocument findByName(String name);

    Optional<EntityDocument> findByBaseId(String baseId);

    List<EntityDocument> findAllByDeletedAtIsNull();

    long countByNameAndBaseIdAndDeletedAtIsNull(String name, String baseId);

    List<EntityDocument> findAllByBaseIdAndDeletedAtIsNull(String baseId);
}
