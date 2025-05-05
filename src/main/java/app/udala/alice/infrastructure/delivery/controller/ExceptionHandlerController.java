package app.udala.alice.infrastructure.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.udala.alice.infrastructure.delivery.dto.ErrorResponseDto;
import app.udala.alice.shared.exception.DataBaseNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DataBaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleDataBaseNotFoundException(DataBaseNotFoundException ex) {
        return new ErrorResponseDto("DataBase Not Found", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleGenericException(RuntimeException ex) {
        return new ErrorResponseDto("Unknown Error", ex.getMessage());
    }
}
