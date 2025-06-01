package app.udala.alice.infrastructure.delivery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.udala.alice.application.port.VectorIndexerUseCase;

@RestController
@RequestMapping("/ai")
public class AIController {

    private final VectorIndexerUseCase vectorUseCase;

    public AIController(VectorIndexerUseCase vectorUseCase) {
        this.vectorUseCase = vectorUseCase;
    }

    @PostMapping("/generate-embeddings")
    public List<Float> generateEmbeddings(@RequestBody String entity) {
        return this.vectorUseCase.textToEmbed(entity);
    }

}
