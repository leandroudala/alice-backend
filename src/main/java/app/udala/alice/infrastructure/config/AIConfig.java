package app.udala.alice.infrastructure.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import app.udala.alice.EmbeddingServiceGrpc;
import app.udala.alice.GenerativeServiceGrpc.GenerativeServiceBlockingStub;
import app.udala.alice.application.port.VectorIndexerUseCase;
import app.udala.alice.application.usecase.QdrantIndexerUseCase;
import io.qdrant.client.QdrantClient;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Configuration
public class AIConfig {

    @GrpcClient("embeddingService")
    private EmbeddingServiceGrpc.EmbeddingServiceBlockingStub embedding;

    @GrpcClient("embeddingService")
    private GenerativeServiceBlockingStub generativeService;

    @Bean
    VectorIndexerUseCase vectorIndexerUseCase(QdrantClient qdrantClient, @Qualifier("dynamicMongoTemplate") MongoTemplate mongoRepository) {
        return new QdrantIndexerUseCase(qdrantClient, this.embedding, this.generativeService, mongoRepository);
    }

}
