package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(
        String username,
        String email,
        String password,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("location_id")
        @NotNull
        Integer locationId
) {}
