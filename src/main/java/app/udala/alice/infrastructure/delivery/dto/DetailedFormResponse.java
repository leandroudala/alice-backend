package app.udala.alice.infrastructure.delivery.dto;

import java.util.List;

public class DetailedFormResponse {
    private String entityId;
    private String name;
    private String description;
    private List<FieldDetailsResponse> fields;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FieldDetailsResponse> getFields() {
        return this.fields;
    }

    public void setFields(List<FieldDetailsResponse> fields) {
        this.fields = fields;
    }

}
