package it.unicam.cs.agritrace.dtos.responses;

import java.time.Instant;

public record ProductCreationResponse(Integer id, String status, Instant createdAt) { }
