package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
    ESEMPIO DI JSON:
    {
        "target_id": 1
    }

    può essere sia un package che un product
*/

public record DeletePayload(
        @JsonProperty("target_id")
        Integer targetId
) {}
