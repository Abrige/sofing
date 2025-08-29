package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.agritrace.dtos.common.PackageItemDTO;

import java.math.BigDecimal;
import java.util.List;

/*
    ESEMPIO DI JSON:
    {
        "name": "Pacchetto bellissimo",
        "description": "Il miglior pacchetto del mondo",
        "category_id": 3,
        "cultivation_method_id": 2,
        "harvest_season_id": 2,
        "producer_id": 4
    }
*/

public record AddPackagePayload(
        String name,
        String description,
        BigDecimal price,
        @JsonProperty("producer_id")
        Integer producerId,
        List<PackageItemDTO> items
) {}
