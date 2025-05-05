package app.udala.alice.infrastructure.delivery.dto;

public class ErrorResponseDto {
    private final String message;
    private final String error;
    private final String category;

    public ErrorResponseDto(String category, String message, String error) {
        this.message = message;
        this.error = error;
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return this.error;
    }

    public String getCategory() {
        return this.category;
    }

}
