package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.agritrace.enums.StatusType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/*
        JSON DI ESEMPIO:
        {
            "request_id":48,
            "action": "accept",
            "decision_notes": "prova prova"
        }
*/

public record ReviewRequestDTO(
        @JsonProperty("request_id")
        @NotNull
        @Min(1)
        int requestId,
        @NotNull
        StatusType action,
        @JsonProperty("decision_notes")
        String decisionNotes) {}
