package app.udala.alice.shared.exception;

public class BaseNotFoundException extends EntityNotFoundException {

    public BaseNotFoundException(String id) {
        super("Base not found. Id: " + id, "Base Not Found", "BASE_NOT_FOUND");
    }

}
