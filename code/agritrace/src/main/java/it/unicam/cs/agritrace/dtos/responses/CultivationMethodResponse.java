package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CultivationMethodResponse(
        @JsonProperty("category_id")
        Integer categoryId,
        String name,
        String description
) {}
