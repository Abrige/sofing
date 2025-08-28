package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  // Trova tutti i prodotti non cancellati con la soft delete
  List<Product> findByIsDeletedIsFalse();

  // Trova tutti i prodotti cancellati con la soft delete
  List<Product> findByIsDeletedIsTrue();

  // Trova il prodotto in base al nome
  Product findByName(String name);

  // Trova il prodotto (NON CANCELLATO) in base all'id
  Optional<Product> findByIdAndIsDeletedFalse(Integer id);
  // Trova i prodotti in base all'azienda
  List<Product> findByProducerAndIsDeletedFalse(Company company);
}