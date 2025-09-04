package it.unicam.cs.agritrace.exceptions;

public class DbTableNotFoundException extends RuntimeException {
    public DbTableNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
