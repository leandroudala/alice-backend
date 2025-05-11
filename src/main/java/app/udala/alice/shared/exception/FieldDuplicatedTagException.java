package app.udala.alice.shared.exception;

public class FieldDuplicatedTagException extends EntityDuplicatedException {
    public FieldDuplicatedTagException(String tag) {
        super("The provided tag is already in use: " + tag, "Duplicated Tag", "DUPLICATED_FIELD_TAG_EXCEPTION");
    }
}
