package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/*
    ESEMPIO DI JSON
    {
      "product_listing_id": 89,
      "quantity": 1,
      "unit_price": 20.00
    }
*/

public record OrderItemRequest(
        @JsonProperty("product_listing_id")
        @NotNull
        Integer productListingId,
        @Positive
        int quantity,
        @Positive
        @JsonProperty("unit_price")
        BigDecimal unitPrice
) {
}
