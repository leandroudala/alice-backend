package app.udala.alice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import app.udala.alice.infrastructure.persistence.entity.FieldDocument;

public interface FieldMongoRepository extends MongoRepository<FieldDocument, String> {
    List<FieldDocument> findByEntityIdAndDeletedAtIsNull(String entityId);

    Optional<FieldDocument> findByTagAndDeletedAtIsNull(String tag);

    List<FieldDocument> findAllByEntityIdAndDeletedAtIsNull(String entityId);
}
