package it.unicam.cs.agritrace.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer userId) {
        super("User not found with ID " + userId);
    }
}
