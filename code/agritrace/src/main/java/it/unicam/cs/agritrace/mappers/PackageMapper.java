package it.unicam.cs.agritrace.mappers;

import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.common.PackageItemDTO;
import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.model.TypicalPackage;

import java.util.List;

public final class PackageMapper {

    public PackageMapper(){
    }

    public static PackageDTO toDTO(TypicalPackage pkg) {
        List<PackageItemDTO> packageItems = pkg.getTypicalPackageItems().stream()
                .map(packageItem -> new PackageItemDTO(
                        new ProductDTO(
                                packageItem.getProduct().getId(),
                                packageItem.getProduct().getName(),
                                packageItem.getProduct().getDescription(),
                                packageItem.getProduct().getCategory().getName(),
                                packageItem.getProduct().getCultivationMethod().getName(),
                                packageItem.getProduct().getHarvestSeason().getName(),
                                packageItem.getProduct().getProducer().getName()
                        ),
                        packageItem.getQuantity()
                ))
                .toList();

        return new PackageDTO(
                pkg.getId(),
                pkg.getName(),
                pkg.getDescription(),
                pkg.getPrice(),
                packageItems
        );
    }
}
