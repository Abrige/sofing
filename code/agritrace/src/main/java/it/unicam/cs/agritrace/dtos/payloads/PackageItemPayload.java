package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;

public record PackageItemPayload(
        @Min(1)
        @JsonProperty("product_id")
        int productId,
        @Min(1)
        int quantity
) {}
