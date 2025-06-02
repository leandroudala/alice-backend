package app.udala.alice.shared.exception;

public class MongoDocumentNotFoundException extends EntityNotFoundException {
    public MongoDocumentNotFoundException(String documentId) {
        super(documentId);
    }
}
