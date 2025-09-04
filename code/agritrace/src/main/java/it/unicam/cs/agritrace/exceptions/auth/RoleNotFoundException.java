package it.unicam.cs.agritrace.exceptions.auth;


public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String roleName) {
        super("Ruolo non trovato: " + roleName);
    }
}
