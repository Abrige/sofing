package it.unicam.cs.agritrace.exceptions;

public class PayloadParsingException extends RuntimeException {
    public PayloadParsingException(String message) {
        super(message);
    }
    public PayloadParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
