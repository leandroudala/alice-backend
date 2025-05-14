package app.udala.alice.shared.exception;

public class BaseDuplicatedNameException extends EntityDuplicatedException {

    public BaseDuplicatedNameException(String name) {
        super(String.format("Base with name '%s' already exists.", name), "Duplicated Base", "DUPLICATED_BASE_EXCEPTION");
    }

}
