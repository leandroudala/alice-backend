package app.udala.alice.infrastructure.delivery.dto;

public class FieldCreateRequest {
    private String name;
    private String tag;
    private String dataType;
    private String description;
    private boolean required;
    private String baseId;

    public FieldCreateRequest() {
        // empty constructor
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean getRequired() {
        return this.required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getBaseId() {
        return this.baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

}
