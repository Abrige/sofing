package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record OrderDTO(
        @JsonProperty("order_id")
        int orderId,
        UserDTO buyer,
        CompanyDTO seller,
        @JsonProperty("total_amount")
        BigDecimal totalAmount,
        String status,
        @JsonProperty("ordered_at")
        Instant orderedAt,
        @JsonProperty("delivery_date")
        LocalDate deliveryDate,
        @JsonProperty("order_items")
        Set<OrderItemDTO> orderItems) {
}
