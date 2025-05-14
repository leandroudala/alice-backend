package app.udala.alice.application.port;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import app.udala.alice.infrastructure.persistence.entity.BaseDocument;

public interface BaseRepository {
    Optional<BaseDocument> findByName(@NonNull String name);

    Optional<BaseDocument> findById(@NonNull String id);

    List<BaseDocument> findAll();

    List<BaseDocument> findAllByDeletedAtIsNull();

    BaseDocument insert(BaseDocument base);

    BaseDocument save(BaseDocument base);

    void delete(BaseDocument base);
}
