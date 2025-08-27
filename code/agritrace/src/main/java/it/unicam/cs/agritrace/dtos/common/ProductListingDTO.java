package it.unicam.cs.agritrace.dtos.common;

import java.math.BigDecimal;

public record ProductListingDTO(String name, CompanyDTO seller, BigDecimal price) {
}
