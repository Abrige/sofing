package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddRawMaterialPayload(
        @JsonProperty("product_id")
        int productId,
        String description
){}
