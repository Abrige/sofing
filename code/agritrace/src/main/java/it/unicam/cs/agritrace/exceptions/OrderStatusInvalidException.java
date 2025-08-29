package it.unicam.cs.agritrace.exceptions;

public class OrderStatusInvalidException extends RuntimeException {
    public OrderStatusInvalidException(String message) {
        super(message);
    }
    public OrderStatusInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}

