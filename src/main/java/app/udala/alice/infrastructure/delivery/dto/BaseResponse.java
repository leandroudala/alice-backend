package app.udala.alice.infrastructure.delivery.dto;

public class BaseResponse {
    private final String id;
    private final String name;

    public BaseResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
