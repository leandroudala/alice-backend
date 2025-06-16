package app.udala.alice.application.port;

import java.util.List;

public interface VectorIndexerUseCase {
    public List<Float> textToEmbed(String text);
    public void indexDocument(String documentId, String collectionName);
    public String search(String prompt, Integer limit);
}
