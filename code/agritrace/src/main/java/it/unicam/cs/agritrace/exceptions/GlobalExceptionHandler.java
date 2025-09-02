package it.unicam.cs.agritrace.exceptions;

import it.unicam.cs.agritrace.dtos.errors.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPackageRequestException.class)
    public ResponseEntity<ApiError> handleInvalidPackage(InvalidPackageRequestException ex, HttpServletRequest request) {
        log.warn("Invalid package request at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid package request: " + ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DbTableNotFoundException.class)
    public ResponseEntity<ApiError> handleDbTableNotFound(DbTableNotFoundException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Quelli che avevi già...
    @ExceptionHandler(PayloadParsingException.class)
    public ResponseEntity<ApiError> handlePayloadError(PayloadParsingException ex, HttpServletRequest request) {
        log.warn("Payload parsing failed at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Payload parsing error at " + request.getRequestURI() + ": " + ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected server error at " + request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
