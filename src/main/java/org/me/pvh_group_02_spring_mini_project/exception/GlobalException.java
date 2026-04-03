package org.me.pvh_group_02_spring_mini_project.exception;

import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail handleNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8090/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");

        // Collect field validation errors
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        // Add errors to ProblemDetail as extra properties
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ProblemDetail handleDuplicateUser(DuplicateUserException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Conflict");
        problemDetail.setType(URI.create("http://localhost:8090/errors/duplicate-user"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException b) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, String> errors = new HashMap<>();
        errors.put("eventDate", b.getMessage());
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("errors", errors);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    //Handle Exception for FileNotFound
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
}

