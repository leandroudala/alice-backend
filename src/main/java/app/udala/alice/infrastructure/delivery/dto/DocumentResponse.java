package app.udala.alice.infrastructure.delivery.dto;

import java.util.Map;

import org.bson.Document;

public class DocumentResponse extends Document {

    public DocumentResponse(Map<String, Object> document) {
        super(document);
        this.generateId();
    }

    private void generateId() {
        if (!this.containsKey("_id")) {
            return;
        }

        String id = this.getObjectId("_id").toHexString();
        this.put("id", id);
        this.remove("_id");
    }
}
