package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;

@Entity
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Molte righe possono appartenere a un solo carrello
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

    // Molte righe possono riferirsi allo stesso prodotto
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductListing product;

    // L'attributo chiave della classe di associazione!
    private int quantity;

    // Getters and Setters...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public ProductListing getProduct() {
        return product;
    }

    public void setProduct(ProductListing product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}