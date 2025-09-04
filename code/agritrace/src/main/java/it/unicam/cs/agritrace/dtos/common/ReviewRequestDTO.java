package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.agritrace.enums.StatusType;

public record ReviewRequestDTO(
        @JsonProperty("request_id")
        int requestId,
        StatusType action,
        @JsonProperty("decision_notes")
        String decisionNotes) {}
