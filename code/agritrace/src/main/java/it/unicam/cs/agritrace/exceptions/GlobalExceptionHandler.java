package it.unicam.cs.agritrace.exceptions;

import it.unicam.cs.agritrace.dtos.errors.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;

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

    @ExceptionHandler(OrderStatusInvalidException.class)
    public ResponseEntity<ApiError> handleOrderStatusInvalid(OrderStatusInvalidException ex, HttpServletRequest request) {
        log.warn("Invalid order status at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid order status: " + ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(PayloadParsingException.class)
    public ResponseEntity<ApiError> handlePayloadError(PayloadParsingException ex, HttpServletRequest request) {
        log.warn("Payload parsing failed at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Payload parsing error at " + request.getRequestURI() + ": " + ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiError> handleProductNotFound(ProductNotFoundException ex, HttpServletRequest request) {
        log.warn("Product not found at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND,
                "Product not found: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Resource not found at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND,
                "Resource not found: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        log.warn("User not found at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND,
                "User not found: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DbTableNotFoundException.class)
    public ResponseEntity<ApiError> handleDbTableNotFound(DbTableNotFoundException ex, HttpServletRequest request) {
        log.warn("DbTable not found at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND,
                "Database table not found: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> handleValidation(HandlerMethodValidationException ex,
                                                     HttpServletRequest request) {
        // Qui puoi estrarre i dettagli della validazione fallita
        String details = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList()
                .toString();

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failure",
                LocalDateTime.now(),
                details
        );

        return ResponseEntity.badRequest().body(error);
    }

    // handler catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex,
                                                  HttpServletRequest request) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected server error at " + request.getRequestURI(),
                LocalDateTime.now(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
