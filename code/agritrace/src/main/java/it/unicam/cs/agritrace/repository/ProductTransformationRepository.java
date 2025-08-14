package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductTransformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTransformationRepository extends JpaRepository<ProductTransformation, Integer> {
}