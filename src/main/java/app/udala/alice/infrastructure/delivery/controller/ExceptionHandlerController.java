package app.udala.alice.infrastructure.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.udala.alice.infrastructure.delivery.dto.ErrorResponseDto;
import app.udala.alice.shared.exception.DataBaseDuplicatedNameException;
import app.udala.alice.shared.exception.DataBaseNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DataBaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleDataBaseNotFoundException(DataBaseNotFoundException ex) {
        return new ErrorResponseDto("DATABASE_NOT_FOUND", "DataBase Not Found", ex.getMessage());
    }

    @ExceptionHandler(DataBaseDuplicatedNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleDataBaseNotFoundException(DataBaseDuplicatedNameException ex) {
        return new ErrorResponseDto("DUPLICATED_DATABASE", "Duplicated Data Base", ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleGenericException(RuntimeException ex) {
        ex.printStackTrace();
        return new ErrorResponseDto("UNKNOWN", "Unknown Error", ex.getMessage());
    }
}
