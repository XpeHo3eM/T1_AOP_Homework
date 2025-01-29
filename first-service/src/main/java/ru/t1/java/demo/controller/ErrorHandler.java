package ru.t1.java.demo.controller;


import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final RuntimeException ex) {
        return Map.of(
                "status", "NOT FOUND",
                "message", ex.getMessage()
        );
    }

    @ExceptionHandler({ValidateException.class,
            ValidationException.class,
            MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotValidException(final RuntimeException ex) {
        return Map.of(
                "status", "BAD REQUEST",
                "message", ex.getMessage()
        );
    }
}
