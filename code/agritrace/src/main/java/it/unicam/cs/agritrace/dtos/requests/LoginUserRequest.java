package it.unicam.cs.agritrace.dtos.requests;

public record LoginUserRequest(
        String email,
        String password
) {
}
