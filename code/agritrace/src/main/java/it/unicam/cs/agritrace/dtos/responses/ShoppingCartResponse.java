package it.unicam.cs.agritrace.dtos.responses;

import it.unicam.cs.agritrace.model.ShoppingCart;
import it.unicam.cs.agritrace.model.ShoppingCartItem;

import java.util.List;

public record ShoppingCartResponse (
        List<ShoppingCartItemResponse> items,
        double totalAmount) {
}
