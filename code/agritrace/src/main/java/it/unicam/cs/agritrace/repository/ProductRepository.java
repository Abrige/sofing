package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}