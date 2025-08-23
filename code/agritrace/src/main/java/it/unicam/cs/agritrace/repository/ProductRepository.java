package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
  // trova tutti i prodotti non cancellati con la soft delete
  List<Product> findByIsDeletedIsFalse();
  // trova tutti i prodotti cancellati con la soft delete
  List<Product> findByIsDeletedIsTrue();
  Product findByName(String name);
}