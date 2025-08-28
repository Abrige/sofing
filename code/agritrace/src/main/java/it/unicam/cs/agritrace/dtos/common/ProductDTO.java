package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDTO(
        int id,
        String name,
        String description,
        @JsonProperty("product_category")
        String productCategory,
        @JsonProperty("cultivation_method")
        String cultivationMethod,
        @JsonProperty("harvest_season")
        String harvestSeason,
        String producer
){}
