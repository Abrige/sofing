package it.unicam.cs.agritrace.dtos.requests;

public record ProductCreationRequest(String name,
                                     String description,
                                     Integer categoryId,
                                     Integer cultivationMethodId,
                                     Integer harvestSeasonId,
                                     Integer producerId) {}
