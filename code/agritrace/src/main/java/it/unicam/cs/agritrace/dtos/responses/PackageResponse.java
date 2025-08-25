package it.unicam.cs.agritrace.dtos.responses;

import it.unicam.cs.agritrace.model.TypicalPackage;
import it.unicam.cs.agritrace.model.TypicalPackageItem;

import java.math.BigDecimal;
import java.util.List;

public record PackageResponse(Integer id,
                              String name,
                              String description,
                              BigDecimal price,
                              List<String> productNames) {
    public PackageResponse(TypicalPackage pkg) {
        this(pkg.getId(),
                pkg.getName(),
                pkg.getDescription(),
                pkg.getPrice(),
                pkg.getTypicalPackageItems()
                        .stream()
                        .map(item -> item.getProduct().getName())
                        .toList());
    }
}
