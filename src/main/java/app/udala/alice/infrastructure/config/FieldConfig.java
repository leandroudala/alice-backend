package app.udala.alice.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.udala.alice.application.port.BaseRepository;
import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.GetFieldsUseCase;
import app.udala.alice.application.port.ManageFieldUseCase;
import app.udala.alice.application.usecase.GetFieldsMongoUseCase;
import app.udala.alice.application.usecase.ManageFieldMongoUseCase;
import app.udala.alice.infrastructure.persistence.repository.FieldMongoRepository;
import app.udala.alice.infrastructure.persistence.repository.PersistenceFieldRepository;

@Configuration
public class FieldConfig {
    @Autowired
    FieldMongoRepository repository;

    @Bean
    FieldRepository createFieldRepository() {
        return new PersistenceFieldRepository(repository);
    }
    
    @Bean
    GetFieldsUseCase createGetFieldsMongoUseCase(@Autowired FieldRepository repository) {
        return new GetFieldsMongoUseCase(repository);
    }

    @Bean
    ManageFieldUseCase createManageFieldsUseCase(@Autowired FieldRepository repository, @Autowired BaseRepository baseRepository) {
        return new ManageFieldMongoUseCase(repository, baseRepository);
    }
}
