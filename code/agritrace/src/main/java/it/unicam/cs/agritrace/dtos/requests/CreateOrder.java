package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

/*
        ESEMPIO DI JSON
        {
          "buyer_id": 101,
          "seller_id": 202,
          "delivery_date": "2025-09-10",
          "delivery_location_id": 3,
          "items": [
            {
              "product_listing_id": 55,
              "quantity": 2,
              "unit_price": 12.50
            },
            {
              "product_listing_id": 89,
              "quantity": 1,
              "unit_price": 20.00
            }
          ]
        }
*/

public record CreateOrder(
        @JsonProperty("buyer_id")
        @NonNull
        Integer buyerId,
        @JsonProperty("seller_id")
        @NonNull
        Integer sellerId,
        @JsonProperty("delivery_date")
        @NonNull
        LocalDate deliveryDate,
        @JsonProperty("delivery_location_id")
        @NonNull
        Integer deliveryLocationId,
        @NotEmpty
        List<OrderItemRequest> items
) {}
