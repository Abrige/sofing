package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
}