package app.udala.alice.infrastructure.delivery.dto;

public class BaseCreateRequest {
    private String name;
    private String description;

    public BaseCreateRequest() {
        // Default constructor
    }

    public BaseCreateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
