package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateOrderStatusRequest(
        @JsonProperty("order_id")
        int orderId,
        String status
){}
