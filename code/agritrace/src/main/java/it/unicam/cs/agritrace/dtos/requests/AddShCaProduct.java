package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Un DTO per mappare la richiesta JSON in entrata
public record AddShCaProduct(
        @JsonProperty("product_listing_id")
        @Min(1)
        @NotNull
        Integer productListingId,
        @Min(1)
        @NotNull
        Integer quantity) {
}
