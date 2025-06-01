package app.udala.alice.application.port;

import java.util.List;

public interface VectorIndexerUseCase {
    public List<Float> textToEmbed(String text);
    public void indexDocument(String collection, String document);
}
