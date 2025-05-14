package app.udala.alice.infrastructure.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.udala.alice.infrastructure.delivery.dto.ErrorResponseDto;
import app.udala.alice.shared.exception.EntityDuplicatedException;
import app.udala.alice.shared.exception.EntityNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorResponseDto(ex.getCategory(), ex.getTitle(), ex.getMessage());
    }

    @ExceptionHandler(EntityDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleBaseNotFoundException(EntityDuplicatedException ex) {
        return new ErrorResponseDto(ex.getCategory(), ex.getTitle(), ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleGenericException(RuntimeException ex) {
        ex.printStackTrace();
        return new ErrorResponseDto("UNKNOWN", "Unknown Error", ex.getMessage());
    }
}
