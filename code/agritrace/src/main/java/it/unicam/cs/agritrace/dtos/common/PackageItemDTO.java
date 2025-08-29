package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PackageItemDTO(
        @JsonProperty("product_id")
        int productId,
        int quantity
) {}
