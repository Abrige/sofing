package it.unicam.cs.agritrace.dtos.responses;

import java.time.Instant;
import java.util.Map;

public record ResponseRequest(
        Integer id,
        String requesterUsername,
        String curatorName,
        String statusName,
        String requestType,
        Instant createdAt,
        Instant reviewedAt,
        String decisionNotes,
        Map<String,Object> payload
) {}
