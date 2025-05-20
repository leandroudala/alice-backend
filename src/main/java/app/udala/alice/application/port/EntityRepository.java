package app.udala.alice.application.port;

import java.util.List;
import java.util.Optional;

import app.udala.alice.domain.entity.Entity;

public interface EntityRepository {
    Optional<Entity> findByBaseId(String baseId);
    List<Entity> findAllByDeletedAtIsNull();
    long countByNameAndBaseIdAndDeletedAtIsNull(String name, String baseId);
    Entity save(Entity entity);
    List<Entity> findAllByBaseIdAndDeletedAtIsNull(String baseId);
    Optional<Entity> findById(String id);
    void delete(Entity entity);
}
