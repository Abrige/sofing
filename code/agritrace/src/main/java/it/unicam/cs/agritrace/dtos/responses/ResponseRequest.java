package it.unicam.cs.agritrace.dtos.responses;

import java.time.Instant;
<<<<<<< HEAD

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
=======
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
>>>>>>> origin/main
) {}
