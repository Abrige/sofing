package it.unicam.cs.agritrace.dtos.responses;

public record ShoppingCartItemResponse(int productId,
                                       String productName,
                                       int quantity,
                                       double unitPrice,
                                       double subtotal) {
}
