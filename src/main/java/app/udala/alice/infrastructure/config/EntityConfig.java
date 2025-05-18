package app.udala.alice.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.udala.alice.application.port.EntityManagementUseCase;
import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.application.usecase.EntityManagementMongoUseCase;
import app.udala.alice.infrastructure.persistence.repository.EntityMongoRepository;
import app.udala.alice.infrastructure.persistence.repository.PersistenceEntityRepository;

@Configuration
public class EntityConfig {

    @Autowired
    private EntityMongoRepository mongoRepository;

    @Bean
    EntityRepository createEntityRepository() {
        return new PersistenceEntityRepository(this.mongoRepository);
    }
    
    @Bean
    EntityManagementUseCase createEntityManagementUseCase(EntityRepository repository) {
        return new EntityManagementMongoUseCase(repository);
    }
}
