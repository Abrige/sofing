package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AddProductToListing (@JsonProperty("product_id") int product_id,
                                    BigDecimal price,
                                    @JsonProperty("quantity_available") int quantityAvailable,
                                    @JsonProperty("unit_of_measure") String unitOfMeasure) {
}
