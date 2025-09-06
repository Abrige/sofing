package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.agritrace.model.Location;
import it.unicam.cs.agritrace.model.OrderItem;
import it.unicam.cs.agritrace.model.Status;
import it.unicam.cs.agritrace.model.User;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record OrderResponse(
        Integer id,
        User buyer,
        @JsonProperty("total_amount")
        BigDecimal totaAmount,
        Status status,
        @JsonProperty("ordered_at")
        Instant orderedAt,
        @JsonProperty("delivery_date")
        LocalDate deliveryDate,
        @JsonProperty("delivery_location")
        Location DeliveryLocation,
        @JsonProperty("order_items")
        Set<OrderItem> OrderItems

) { }
