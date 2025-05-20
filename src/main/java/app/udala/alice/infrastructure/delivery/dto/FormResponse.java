package app.udala.alice.infrastructure.delivery.dto;

public class FormResponse {
    private String entityId;
    private String name;


    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
