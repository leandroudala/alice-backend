package app.udala.alice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.udala.alice.EmbeddingServiceGrpc;
import app.udala.alice.application.port.VectorIndexerUseCase;
import app.udala.alice.application.usecase.QdrantIndexerUseCase;
import io.qdrant.client.QdrantClient;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Configuration
public class AIConfig {

    @GrpcClient("embeddingService")
    private EmbeddingServiceGrpc.EmbeddingServiceBlockingStub embedding;

    @Bean
    VectorIndexerUseCase vectorIndexerUseCase(QdrantClient qdrantClient) {
        return new QdrantIndexerUseCase(qdrantClient, this.embedding);
    }

}
