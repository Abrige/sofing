package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record   ProductListingResponse (@JsonProperty("product_id") int productId,
                                      String name,
                                     String seller,
                                     BigDecimal price,
                                     @JsonProperty("quantity_available") int quantityAvailable,
                                     @JsonProperty("unit_of_measure") String unitOfMeasure
                                     ) {
}
