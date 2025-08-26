package it.unicam.cs.agritrace.dtos.requests;


import it.unicam.cs.agritrace.dtos.common.PackageItemDTO;

import java.math.BigDecimal;
import java.util.List;

public record PackageCreationRequest(String name,
                                     String description,
                                     BigDecimal price,
                                     int producer_id,
                                     List<PackageItemDTO> items) {

}

