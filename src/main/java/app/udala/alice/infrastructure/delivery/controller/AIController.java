package app.udala.alice.infrastructure.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<Float> generateEmbeddings(@RequestBody String text) {
        return this.vectorUseCase.textToEmbed(text);
    }

    @PostMapping("/generate-embeddings/{entityId}/{documentId}")
    public ResponseEntity<Void> generateEmbeddingsByDocumentId(@PathVariable String entityId, @PathVariable String documentId) {
        this.vectorUseCase.indexDocument(documentId, entityId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<String> search(@RequestBody String prompt, @RequestParam(required = false, defaultValue = "10") Integer limit) {
        String result = this.vectorUseCase.search(prompt, limit);
        return ResponseEntity.ok(result);
    }

}
