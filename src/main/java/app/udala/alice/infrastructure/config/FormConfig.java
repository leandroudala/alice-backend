package app.udala.alice.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.FormUseCase;
import app.udala.alice.application.usecase.MongoFormUseCase;

@Configuration
public class FormConfig {

    @Bean
    FormUseCase createFormUseCase(@Autowired EntityRepository entityRepository,
            @Autowired FieldRepository fieldRepository) {
        return new MongoFormUseCase(entityRepository, fieldRepository);
    }
}
