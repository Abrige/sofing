package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record UpdateOrderStatusRequest(
        @JsonProperty("order_id")
        int orderId,
        String status,
        @JsonProperty("delivery_date")
        LocalDate deliveryDate
){}
