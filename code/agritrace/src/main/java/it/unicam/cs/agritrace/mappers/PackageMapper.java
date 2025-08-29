package it.unicam.cs.agritrace.mappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.model.TypicalPackage;

import java.util.List;

public final class PackageMapper {

    public PackageMapper(){}

    public static PackageDTO toDTO(TypicalPackage pkg) {
        List<ProductDTO> products = pkg.getTypicalPackageItems().stream()
                .map(item -> new ProductDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getDescription(),
                        item.getProduct().getCategory().getName(),
                        item.getProduct().getCultivationMethod().getName(),
                        item.getProduct().getHarvestSeason().getName(),
                        item.getProduct().getProducer().getName()
                ))
                .toList();

        return new PackageDTO(
                pkg.getId(),
                pkg.getName(),
                pkg.getDescription(),
                pkg.getPrice(),
                products
        );
    }
}
