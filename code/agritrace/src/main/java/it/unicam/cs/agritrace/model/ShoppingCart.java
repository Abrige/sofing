package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Un carrello appartiene a un solo utente
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Un carrello ha molte righe. Cascade e orphanRemoval sono FONDAMENTALI:
    // se salvo un carrello, salva anche le sue righe. Se rimuovo una riga dalla lista, cancellala dal DB.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> items = new ArrayList<>();

    // Getters and Setters...

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }
}