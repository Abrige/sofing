package it.unicam.cs.agritrace.exceptions;

public class DbTableNotFoundException extends RuntimeException {
    public DbTableNotFoundException(Integer tableId) {
        super("Target table not found with ID " + tableId);
    }
}
