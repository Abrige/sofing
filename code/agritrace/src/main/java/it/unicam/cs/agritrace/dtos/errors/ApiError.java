package it.unicam.cs.agritrace.dtos.errors;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(
        int status,
        String error,
        LocalDateTime timestamp,
        String details
) {
    public ApiError(HttpStatus status, String error, String details) {
        this(status.value(), error, LocalDateTime.now(), details);
    }

    public ApiError(HttpStatus status, String error) {
        this(status.value(), error, LocalDateTime.now(), null);
    }
}
