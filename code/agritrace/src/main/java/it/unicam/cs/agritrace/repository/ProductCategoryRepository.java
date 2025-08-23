package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}