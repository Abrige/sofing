package it.unicam.cs.agritrace.exceptions.auth;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username già registrato: " + username);
    }
}
