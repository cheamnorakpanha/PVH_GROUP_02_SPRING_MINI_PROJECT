package org.me.pvh_group_02_spring_mini_project.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.me.pvh_group_02_spring_mini_project.model.response.ApiResponse;
import org.me.pvh_group_02_spring_mini_project.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("type", "about:blank");
        response.put("title", "Bad Request");
        response.put("status", 400);
        response.put("instance", request.getRequestURI());
        response.put("errors", errors);
        response.put("timestamp", Instant.now());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateUser(DuplicateUserException e, HttpServletRequest request) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("type", "about:blank");
        error.put("title", "Conflict");
        error.put("status", 409);
        error.put("detail", e.getMessage());
        error.put("instance", request.getRequestURI());
        error.put("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException b, HttpServletRequest request) {
        Map<String, Object> error = new LinkedHashMap<>();
        error.put("type", "about:blank");
        error.put("title", "Bad Request");
        error.put("status", 400);
        error.put("instance", request.getRequestURI());

        if (b.getErrors() != null && !b.getErrors().isEmpty()) {
            error.put("errors", b.getErrors());
        } else {
            error.put("detail", b.getMessage());
        }

        error.put("timestamp", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("UNAUTHORIZED")
                .message("Authentication required or token is invalid")
                .path("/error")
                .build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {

        Map<String, Object> error = new LinkedHashMap<>();

        error.put("type", "about:blank");
        error.put("title", "Not Found");
        error.put("status", 404);
        error.put("detail", ex.getMessage());
        error.put("instance", request.getRequestURI());
        error.put("timestamp", Instant.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            String fieldName = field.substring(field.lastIndexOf('.') + 1);
            errors.put(fieldName, violation.getMessage());
        });

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("type", "about:blank");
        response.put("title", "Bad Request");
        response.put("status", 400);
        response.put("instance", request.getRequestURI());
        response.put("errors", errors);
        response.put("timestamp", Instant.now());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(CreateHabitException.class)
    public ResponseEntity<Map<String, Object>> handleCreateHabitException(
            CreateHabitException ex,
            HttpServletRequest request
    ) {

        Map<String, Object> error = new LinkedHashMap<>();
        error.put("type", "about:blank");
        error.put("title", "Bad Request");
        error.put("status", 400);
        error.put("instance", request.getRequestURI());
        error.put("errors", ex.getErrors());
        error.put("timestamp", Instant.now());

        return ResponseEntity.badRequest().body(error);
    }
}