package it.unicam.cs.agritrace.dtos.common;

import java.math.BigDecimal;

public record OrderItemDTO(
        ProductListingDTO product,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice) {
}
