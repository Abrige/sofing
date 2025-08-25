package it.unicam.cs.agritrace.dtos.requests;


import it.unicam.cs.agritrace.dtos.TypicalPackageItemDTO;

import java.math.BigDecimal;
import java.util.List;

public record CreatePackageRequest(String name,
                                   String description,
                                   BigDecimal price,
                                   int producer_id,
                                   List<TypicalPackageItemDTO> items) {

}

