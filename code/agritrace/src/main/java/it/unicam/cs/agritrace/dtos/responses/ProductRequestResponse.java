package it.unicam.cs.agritrace.dtos.responses;

import java.time.Instant;

public record ProductRequestResponse(Integer id, String status, Instant createdAt) { }
