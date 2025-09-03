package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
    ESEMPIO DI JSON:
    {
        "product_id": 1,
        "name": "Ciliegina",
        "description": "Una ciliegia molto buonissima",
        "category_id": 3,
        "cultivation_method_id": 2,
        "harvest_season_id": 2,
        "producer_id": 4
    }

    tutti i campi sono opzionali ed è usato anche per l'UPDATE di un product
*/

public record ProductPayload(
        @JsonProperty("product_id")
        Integer productId,
        String name,
        String description,
        @JsonProperty("category_id")
        Integer categoryId,
        @JsonProperty("cultivation_method_id")
        Integer cultivationMethodId,
        @JsonProperty("harvest_season_id")
        Integer harvestSeasonId,
        @JsonProperty("producer_id")
        Integer producerId
){}
