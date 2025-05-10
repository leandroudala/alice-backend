package app.udala.alice.infrastructure.persistence.repository;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.persistence.mapper.FieldPersistenceMapper;

public class PersistenceFieldRepository implements FieldRepository {

    private final FieldMongoRepository repository;

    public PersistenceFieldRepository(FieldMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Field> getFieldsByDataBaseId(String databaseId) {
        return this.repository.findByDatabaseId(databaseId).stream()
                .map(FieldPersistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

}
