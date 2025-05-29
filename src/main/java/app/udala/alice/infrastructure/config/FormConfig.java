package app.udala.alice.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.application.port.FieldRepository;
import app.udala.alice.application.port.FormUseCase;
import app.udala.alice.application.usecase.MongoFormUseCase;

@Configuration
public class FormConfig {

    private static final String DATABASE_CUSTOM_NAME = "aliceFormData";
    private static final String DATABASE_PRIMARY = "alice";

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, DATABASE_PRIMARY);
    }

    @Bean("dynamicMongoTemplate")
    MongoTemplate dynamicMongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, DATABASE_CUSTOM_NAME);
    }

    @Bean
    FormUseCase createFormUseCase(EntityRepository entityRepository,
            FieldRepository fieldRepository, @Qualifier("dynamicMongoTemplate") MongoTemplate dynamicMongoTemplate) {
        return new MongoFormUseCase(entityRepository, fieldRepository, dynamicMongoTemplate);
    }
}
