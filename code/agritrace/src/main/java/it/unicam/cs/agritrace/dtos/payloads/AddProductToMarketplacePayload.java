package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
    ESEMPIO DI JSON:
    {
        "product_id": 1,
        "seller_id": 1,
        "price": 100.0,
        "quantity": 10,
        "unity_of_measure": "kg"
    }
*/

public record AddProductToMarketplacePayload(
        @JsonProperty("product_id") Integer productId,
        @JsonProperty("seller_id") Integer sellerId,
        Double price,
        Integer quantity,
        @JsonProperty("unity_of_measure") String unityOfMeasure
) {}
