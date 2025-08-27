package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
    ESEMPIO DI JSON:
    {
        "product_id": 1,
    }
*/

public record DeleteProductPayload(
        @JsonProperty("product_id") Integer productId
) {}
