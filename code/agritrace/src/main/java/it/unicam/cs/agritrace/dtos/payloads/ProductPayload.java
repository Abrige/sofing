package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

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
