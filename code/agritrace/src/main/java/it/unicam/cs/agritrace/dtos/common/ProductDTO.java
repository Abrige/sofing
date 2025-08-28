package it.unicam.cs.agritrace.dtos.common;

import it.unicam.cs.agritrace.model.Product;

public record ProductDTO(int id, String name, String category ) {
    public ProductDTO(Product product) {
        this (product.getId(), product.getName(), product.getCategory().getName());
    }
}

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
