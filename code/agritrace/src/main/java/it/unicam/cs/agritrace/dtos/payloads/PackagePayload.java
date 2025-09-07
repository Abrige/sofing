package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record PackagePayload(
        @JsonProperty("package_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Integer packageId,
        String name,
        String description,
        BigDecimal price,
        @JsonProperty("producer_id")
        Integer producerId,
        List<PackageItemPayload> items
) {}
