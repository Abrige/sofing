package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(String username,
                      @JsonProperty("first_name")
                      String firstName,
                      @JsonProperty("last_name")
                      String lastName,
                      String email,
                      String phone) {
}
