package app.udala.alice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;

@Configuration
public class QdrantConfig {

    @Bean("qdrantClient")
    QdrantClient qdrantClient() {
        boolean useLayerSecurity = false;

        return new QdrantClient(
            QdrantGrpcClient.newBuilder("localhost", 6334, useLayerSecurity)
            .build()
        );
    }
}
