package it.unicam.cs.agritrace.dtos;

import it.unicam.cs.agritrace.model.Product;

public record ProductDTO(int id, String name, String category ) {
    public ProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getCategory().getName());
    }
}
