package it.unicam.cs.agritrace.exceptions;

import it.unicam.cs.agritrace.dtos.errors.ApiError;
import it.unicam.cs.agritrace.exceptions.auth.EmailAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderStatusInvalidException.class)
    public ResponseEntity<ApiError> handleOrderStatusInvalid(OrderStatusInvalidException ex, HttpServletRequest request) {
        log.warn("Invalid order status at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Invalid order status: " + ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        log.warn("Illegal state at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    // Handler per payload parsing e altre eccezioni di richiesta errata
    @ExceptionHandler({InvalidPackageRequestException.class, PayloadParsingException.class})
    public ResponseEntity<ApiError> handleBadRequest(RuntimeException ex, HttpServletRequest request) {
        log.warn("Bad request at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
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

    // Email già presente nel db in fase di registrazione
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExists(EmailAlreadyExistsException ex, HttpServletRequest request) {
        log.warn("Email already exists at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
               HttpStatus.CONFLICT,
               "Email already exists: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
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

    // Handler per validazioni dei DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();

        log.warn("Validation failed at {} {}: {}", request.getMethod(), request.getRequestURI(), errors);

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                LocalDateTime.now(),
                errors.toString()
        );
        return ResponseEntity.badRequest().body(error);
    }

    // Accesso negato (utente autenticato ma senza ruolo)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Access denied at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                "Access denied: " + ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // Utente non autenticato
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        log.warn("Authentication failed at {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());
        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Authentication failed: " + ex.getMessage(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
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
