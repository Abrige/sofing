package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record   ProductListingResponse (@JsonProperty("product_id") int productId,
                                      String name,
                                     String seller
) {
}
