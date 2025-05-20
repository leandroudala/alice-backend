package app.udala.alice.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.persistence.entity.FieldDocument;
import app.udala.alice.infrastructure.persistence.mapper.FieldPersistenceMapper;

public class PersistenceFieldRepository implements FieldRepository {

    private final FieldMongoRepository repository;

    public PersistenceFieldRepository(FieldMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Field> getFieldsByEntityId(String baseId) {
        return this.repository.findByEntityIdAndDeletedAtIsNull(baseId).stream()
                .map(FieldPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Field insert(Field field) {
        FieldDocument document = FieldPersistenceMapper.toEntity(field);
        FieldDocument saved = this.repository.insert(document);
        return FieldPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Field> findById(String id) {
        Optional<FieldDocument> found = this.repository.findById(id);
        if (found.isEmpty()) {
            return Optional.empty();
        }

        FieldDocument entity = found.get();
        return Optional.of(FieldPersistenceMapper.toDomain(entity));
    }

    @Override
    public Optional<Field> findByTagAndDeletedAtIsNull(String tag) {
        Optional<FieldDocument> found = this.repository.findByTagAndDeletedAtIsNull(tag);
        if (found.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(FieldPersistenceMapper.toDomain(found.get()));
    }

    @Override
    public Field save(Field model) {
        FieldDocument document = FieldPersistenceMapper.toEntity(model);
        FieldDocument saved = this.repository.save(document);
        return FieldPersistenceMapper.toDomain(saved);
    }

    @Override
    public void delete(Field model) {
        FieldDocument document = FieldPersistenceMapper.toEntity(model);
        document.preRemove();
        this.repository.save(document);
    }

}
