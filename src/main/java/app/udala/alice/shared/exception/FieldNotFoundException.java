package app.udala.alice.shared.exception;

public class FieldNotFoundException extends EntityNotFoundException {

    public FieldNotFoundException(String id) {
        super("Field not found. Field id: " + id, "Field Not Found", "FIELD_NOT_FOUND");
    }
    
}
