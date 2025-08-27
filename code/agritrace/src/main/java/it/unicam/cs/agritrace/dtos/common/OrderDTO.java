package it.unicam.cs.agritrace.dtos.common;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record OrderDTO(
        UserDTO buyer,
        CompanyDTO seller,
        BigDecimal totalAmount,
        String status,
        Instant orderedAt,
        LocalDate deliveryDate,
        Set<OrderItemDTO> orderItems) {
}
