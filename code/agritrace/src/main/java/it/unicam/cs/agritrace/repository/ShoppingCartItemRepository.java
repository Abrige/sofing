package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    // Trova un item del carrello per prodotto singolo
    Optional<ShoppingCartItem> findByCartAndProductListing(ShoppingCart cart, ProductListing product);

    // Trova un item del carrello per pacchetto
    Optional<ShoppingCartItem> findByCartAndTypicalPackage(ShoppingCart cart, TypicalPackage typicalPackage);

}