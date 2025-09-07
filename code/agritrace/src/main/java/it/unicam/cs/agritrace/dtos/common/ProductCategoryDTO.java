package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductCategoryDTO(
        @JsonProperty("category_id")
        Integer categoryId,
        String name,
        String description
) {}
