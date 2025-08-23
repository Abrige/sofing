package it.unicam.cs.agritrace.dtos.responses;

import java.time.Instant;

public record ResponseRequest(
        Integer id,
        Integer requesterId,
        Integer targetTableId,
        Integer targetId,
        String requestType,
        Integer curatorId,
        String decisionNotes,
        Instant createdAt,
        Instant reviewedAt,
        Integer statusId,
        String payload
) {}
