package app.udala.alice.shared.exception;

public class DataBaseNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataBaseNotFoundException(String id) {
        super(String.format("DataBase not found. Id: %s", id));
    }

}
