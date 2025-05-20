package app.udala.alice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.domain.entity.Entity;
import app.udala.alice.infrastructure.persistence.entity.EntityDocument;
import app.udala.alice.infrastructure.persistence.mapper.EntityPersistenceMapper;

public class PersistenceEntityRepository implements EntityRepository {

    private final EntityMongoRepository repository;

    public PersistenceEntityRepository(EntityMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Entity> findByBaseId(String baseId) {
        Optional<EntityDocument> found = this.repository.findByBaseId(baseId);
        if (found.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(EntityPersistenceMapper.toDomain(found.get()));
    }

    @Override
    public List<Entity> findAllByDeletedAtIsNull() {
        return this.repository.findAllByDeletedAtIsNull().stream()
                .map(EntityPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public long countByNameAndBaseIdAndDeletedAtIsNull(String name, String baseId) {
        return this.repository.countByNameAndBaseIdAndDeletedAtIsNull(name, baseId);
    }

    @Override
    public Entity save(Entity entity) {
        var document = EntityPersistenceMapper.toDocument(entity);
        var saved = this.repository.save(document);
        return EntityPersistenceMapper.toDomain(saved);
    }

    @Override
    public List<Entity> findAllByBaseIdAndDeletedAtIsNull(String baseId) {
        return this.repository.findAllByBaseIdAndDeletedAtIsNull(baseId).stream()
        .map(EntityPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Entity> findById(String id) {
        return this.repository.findById(id)
                .map(EntityPersistenceMapper::toDomain);
    }

    @Override
    public void delete(Entity entity) {
        EntityDocument document = EntityPersistenceMapper.toDocument(entity);
        document.preRemove();
        this.repository.save(document);
    }

}
