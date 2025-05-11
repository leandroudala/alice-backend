package app.udala.alice.shared.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final String DEFAULT_CATEGORY = "ENTITY_NOT_FOUND";
    private static final String DEFAULT_TITLE = "Entity Not Found";
    private final String title;
    private final String category;

    public EntityNotFoundException(String message) {
        this(message, DEFAULT_TITLE);
    }

    public EntityNotFoundException(String message, String title) {
        this(message, title, DEFAULT_CATEGORY);
    }

    public EntityNotFoundException(String message, String title, String category) {
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
