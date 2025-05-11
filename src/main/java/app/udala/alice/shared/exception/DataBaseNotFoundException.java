package app.udala.alice.shared.exception;

public class DataBaseNotFoundException extends EntityNotFoundException {

    public DataBaseNotFoundException(String id) {
        super("DataBase not found. Id: " + id, "Database Not Found", "DATABASE_NOT_FOUND");
    }

}
