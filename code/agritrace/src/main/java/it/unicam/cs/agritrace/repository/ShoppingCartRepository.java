package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ShoppingCart;
import it.unicam.cs.agritrace.model.ShoppingCartItem;
import it.unicam.cs.agritrace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
  // Trova il carrello di un utente, se esiste
  Optional<ShoppingCart> findByUser(User user);
}