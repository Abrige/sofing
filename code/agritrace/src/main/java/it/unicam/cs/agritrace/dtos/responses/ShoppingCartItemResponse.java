package it.unicam.cs.agritrace.dtos.responses;

public record ShoppingCartItemResponse(
        Integer productListingId,   // se è un prodotto singolo
        Integer typicalPackageId,   // se è un pacchetto
        String itemName,            // nome del prodotto o del pacchetto
        int quantity,
        double unitPrice,
        double subtotal,
        String type                 // "PRODUCT" o "PACKAGE"
) {}
