package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HarvestSeasonResponse(
        @JsonProperty("harvest_season_id")
        Integer harvestSeasonId,
        String name,
        @JsonProperty("month_start")
        Short MonthStart,
        @JsonProperty("month_end")
        Short MonthEnd,
        String hemisphere,
        String description
) {}
