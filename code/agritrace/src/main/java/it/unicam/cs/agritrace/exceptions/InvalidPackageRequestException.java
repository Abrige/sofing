package it.unicam.cs.agritrace.exceptions;

public class InvalidPackageRequestException extends RuntimeException {
    public InvalidPackageRequestException(String message) {
        super(message);
    }
}
