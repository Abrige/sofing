package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductListing;
import it.unicam.cs.agritrace.model.ShoppingCart;
import it.unicam.cs.agritrace.model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    // Trova una riga specifica in un carrello per un dato prodotto
    Optional<ShoppingCartItem> findByCartAndProduct(ShoppingCart cart, ProductListing product);
}