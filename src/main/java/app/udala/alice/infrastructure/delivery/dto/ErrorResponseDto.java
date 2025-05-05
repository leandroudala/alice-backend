package app.udala.alice.infrastructure.delivery.dto;

public class ErrorResponseDto {
    private final String message;
    private final String error;

    public ErrorResponseDto(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return this.error;
    }

}
