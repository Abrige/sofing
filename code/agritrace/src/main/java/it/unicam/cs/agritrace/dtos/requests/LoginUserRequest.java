package it.unicam.cs.agritrace.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginUserRequest(
        @NotEmpty(message = "Email obbligatoria")
        @Email(message = "Email non valida")
        String email,
        @NotEmpty(message = "Password obbligatoria")
        String password
) {
}
