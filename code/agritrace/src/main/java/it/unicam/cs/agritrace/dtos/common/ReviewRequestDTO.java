package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.agritrace.enums.StatusType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(
        @JsonProperty("request_id")
        @NotNull
        @Min(1)
        int requestId,
        @NotNull
        StatusType action,
        @JsonProperty("decision_notes")
        String decisionNotes) {}
