package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    Optional<ProductCategory> findById(Integer id);
    boolean existsById(Integer id);
}