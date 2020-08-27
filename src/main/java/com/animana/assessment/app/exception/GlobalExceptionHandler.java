package com.animana.assessment.app.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = SearchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestException(SearchException exception) {
        ErrorResponse errorResponse = new ErrorResponse("BAD_REQUEST", exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

@Getter
@Setter
class ErrorResponse {
    private String type;
    private String message;
    private LocalDateTime timestamp;
    private int status;

    ErrorResponse(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
