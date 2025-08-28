package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record OrderItemDTO(
        ProductListingDTO product,
        int quantity,
        @JsonProperty("unit_price")
        BigDecimal unitPrice,
        @JsonProperty("total_price")
        BigDecimal totalPrice) {
}
