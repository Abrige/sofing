package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

public record RequestResponse(
        @JsonProperty("id")
        Integer id,

        @JsonProperty("requester_username")
        String requesterUsername,

        @JsonProperty("curator_name")
        String curatorName,

        @JsonProperty("status")
        String status,

        @JsonProperty("request_type")
        String requestType,

        @JsonProperty("created_at")
        Instant createdAt,

        @JsonProperty("reviewed_at")
        Instant reviewedAt,

        @JsonProperty("decision_notes")
        String decisionNotes,

        @JsonProperty("payload")
        Map<String, Object> payload
) {
}
