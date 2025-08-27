package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.agritrace.dtos.common.PackageItemDTO;

import java.util.List;

public record AddPackagePayload(String name,
                                String description,
                                Double price,
                                @JsonProperty("producer_id") Integer producerId,
                                List<PackageItemDTO> items)
{
}
