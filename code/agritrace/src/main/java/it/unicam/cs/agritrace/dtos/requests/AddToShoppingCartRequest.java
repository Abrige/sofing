package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

// Un DTO per mappare la richiesta JSON in entrata
public record  AddToShoppingCartRequest (
        @JsonProperty("product_id")
        Integer productId,
        int quantity) {
}
