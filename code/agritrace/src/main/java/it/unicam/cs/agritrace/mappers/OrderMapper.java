package it.unicam.cs.agritrace.mappers;

import it.unicam.cs.agritrace.dtos.common.*;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.model.OrderItem;
import it.unicam.cs.agritrace.model.ProductListing;

import java.util.Set;
import java.util.stream.Collectors;

public final class OrderMapper {

    private OrderMapper() { /* utility class */ }

    // Prende una entity Order e la trasforma nel relativo DTO --> OrderDTO
    public static OrderDTO toDto(Order order) {
        if (order == null) {
            return null;
        }

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

    // Prende una entity OrderItemRequest e la trasforma nel relativo DTO --> OrderItemDTO
    private static OrderItemDTO toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        ProductListing pl = orderItem.getProductListing();

        // null-safe (anche se PRODUCT_LISTING_ID è NOT NULL a DB, difendiamoci)
        ProductListingDTO productDto = (pl != null)
                ? new ProductListingDTO(
                (pl.getProduct() != null ? pl.getProduct().getName() : null),
                pl.getPrice()
        )
                : null;

        return new OrderItemDTO(
                productDto,
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getTotalPrice()
        );
    }
}
