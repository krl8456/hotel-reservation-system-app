package com.karol.hotelreservationsystem.exception;

import com.karol.hotelreservationsystem.http.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorResponse<Map<String, String>> body = ApiErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Form validation failed",
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ApiErrorResponse<String>> handleSQLIntegrityConstraintViolationException(
            Exception ex
    ) {
        ApiErrorResponse<String> body = ApiErrorResponse.of(
                HttpStatus.CONFLICT.value(),
                "Duplicate records occurred",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiErrorResponse<String> body = ApiErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleBadCredentialsException(BadCredentialsException ex) {
        ApiErrorResponse<String> body = ApiErrorResponse.of(
                HttpStatus.UNAUTHORIZED.value(),
                "Bad credentials provided",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse<String>> handleAllExceptions(Exception ex) {
        ApiErrorResponse<String> body = ApiErrorResponse.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }
}