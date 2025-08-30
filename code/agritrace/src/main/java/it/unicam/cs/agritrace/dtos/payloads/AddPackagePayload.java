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
        "price": 100,
        "producer_id": 2,
        "package":
        [
                {
                "product_id": 1,
                "quantity": 3
                },
                {
                "product_id": 2,
                "quantity": 4
                },
                {
                "product_id": 5,
                "quantity": 2
                },
         ]
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
