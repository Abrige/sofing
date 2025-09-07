package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record OrderItemDTO(
        ProductListingDTO product,     // null se è un pacchetto
        PackageDTO typicalPackage,     // null se è un prodotto singolo
        int quantity,
        @JsonProperty("unit_price")
        BigDecimal unitPrice,
        @JsonProperty("total_price")
        BigDecimal totalPrice) {
}
