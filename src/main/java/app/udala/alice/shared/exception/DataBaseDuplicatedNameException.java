package app.udala.alice.shared.exception;

public class DataBaseDuplicatedNameException extends EntityDuplicatedException {

    public DataBaseDuplicatedNameException(String name) {
        super(String.format("DataBase with name '%s' already exists.", name), "Duplicated Data Base", "DUPLICATED_DATABASE_EXCEPTION");
    }

}
