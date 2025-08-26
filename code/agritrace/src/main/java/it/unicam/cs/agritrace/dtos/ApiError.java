package it.unicam.cs.agritrace.dtos;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(
        int status,
        String error,
        LocalDateTime timestamp
) {
    public ApiError(HttpStatus status, String error) {
        this(status.value(), error, LocalDateTime.now());
    }
}
