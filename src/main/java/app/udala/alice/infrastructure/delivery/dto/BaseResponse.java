package app.udala.alice.infrastructure.delivery.dto;

public class BaseResponse {
    private final String id;
    private final String description;
    private final String name;

    public BaseResponse(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}
