package it.unicam.cs.agritrace.dtos.common;

import java.math.BigDecimal;
import java.util.List;

public record PackageDTO(
        int id,
        String name,
        String description,
        BigDecimal price,
        List<PackageItemDTO> items
)
{}
