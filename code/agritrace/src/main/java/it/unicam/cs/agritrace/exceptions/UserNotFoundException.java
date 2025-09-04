package it.unicam.cs.agritrace.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User not found username= " + username);
    }
}
