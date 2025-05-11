package app.udala.alice.shared.exception;

public class EntityDuplicatedException extends RuntimeException {
    private final static String DEFAULT_TITLE = "Duplicated Entity Exception";
    private final static String DEFAULT_CATEGORY = "ENTITY_DUPLICATED_EXCEPTION";
    private final String title;
    private final String category;

    public EntityDuplicatedException(String message) {
        this(message, DEFAULT_TITLE);
    }

    public EntityDuplicatedException(String message, String title) {
        this(message, title, DEFAULT_CATEGORY);
    }

    public EntityDuplicatedException(String message, String title, String category) {
        super(message);
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCategory() {
        return this.category;
    }

}
