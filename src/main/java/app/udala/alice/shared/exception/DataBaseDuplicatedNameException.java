package app.udala.alice.shared.exception;

public class DataBaseDuplicatedNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataBaseDuplicatedNameException(String name) {
        super(String.format("DataBase with name '%s' already exists.", name));
    }

}
