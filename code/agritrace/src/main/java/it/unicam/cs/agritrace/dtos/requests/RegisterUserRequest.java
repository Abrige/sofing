package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record RegisterUserRequest(
        @NotEmpty(message = "Username obbligatorio")
        String username,

        @NotEmpty(message = "Email obbligatoria")
        @Email(message = "Email non valida")
        String email,

        @NotEmpty(message = "Password obbligatoria")
        String password,

        @JsonProperty("first_name")
        @NotEmpty(message = "Nome obbligatorio")
        String firstName,

        @JsonProperty("last_name")
        @NotEmpty(message = "Cognome obbligatorio")
        String lastName,

        @JsonProperty("phone_number")
        @Pattern(regexp = "\\+?[0-9]*", message = "Numero di telefono non valido")
        String phoneNumber,

        @JsonProperty("location_id")
        @NotNull(message = "Location obbligatoria")
        @Min(value = 1, message = "Location deve essere maggiore di 0")
        Integer locationId
) {}
