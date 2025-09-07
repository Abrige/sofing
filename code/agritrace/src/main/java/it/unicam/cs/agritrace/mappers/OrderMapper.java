package it.unicam.cs.agritrace.mappers;

import it.unicam.cs.agritrace.dtos.common.*;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.model.OrderItem;
import it.unicam.cs.agritrace.model.ProductListing;
import it.unicam.cs.agritrace.model.TypicalPackage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class OrderMapper {

    private OrderMapper() { /* utility class */ }

    public static OrderDTO toDto(Order order) {
        if (order == null) return null;

        UserDTO buyerDTO = new UserDTO(
                order.getBuyer().getUsername(),
                order.getBuyer().getFirstName(),
                order.getBuyer().getLastName(),
                order.getBuyer().getEmail(),
                order.getBuyer().getPhone()
        );

        Set<OrderItemDTO> orderItems = order.getOrderItems()
                .stream()
                .map(OrderMapper::toOrderItemDto)
                .collect(Collectors.toSet());

        return new OrderDTO(
                order.getId(),
                buyerDTO,
                order.getTotalAmount(),
                StatusMapper.mapStatusToEnum(order.getStatus()).name().toLowerCase(),
                order.getOrderedAt(),
                order.getDeliveryDate(),
                orderItems
        );
    }

    private static OrderItemDTO toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) return null;

        ProductListingDTO productDto = null;
        PackageDTO packageDto = null;

        if (orderItem.getProductListing() != null) {
            ProductListing pl = orderItem.getProductListing();
            productDto = new ProductListingDTO(
                    pl.getProduct() != null ? pl.getProduct().getName() : null,
                    pl.getPrice()
            );
        } else if (orderItem.getTypicalPackage() != null) {
            TypicalPackage tp = orderItem.getTypicalPackage();

            List<PackageItemDTO> packageItems = tp.getTypicalPackageItems().stream()
                    .map(item -> new PackageItemDTO(
                            new ProductDTO(
                                    item.getProduct().getId(),
                                    item.getProduct().getName(),
                                    item.getProduct().getDescription(),
                                    item.getProduct().getCategory() != null ? item.getProduct().getCategory().getName() : null,
                                    item.getProduct().getCultivationMethod() != null ? item.getProduct().getCultivationMethod().getName() : null,
                                    item.getProduct().getHarvestSeason() != null ? item.getProduct().getHarvestSeason().getName() : null,
                                    item.getProduct().getProducer() != null ? item.getProduct().getProducer().getName() : null
                            ),
                            item.getQuantity()
                    ))
                    .toList();

            packageDto = new PackageDTO(
                    tp.getId(),
                    tp.getName(),
                    tp.getDescription(),
                    tp.getPrice(),
                    packageItems
            );
        }

        return new OrderItemDTO(
                productDto,
                packageDto,
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getTotalPrice()
        );
    }
}
