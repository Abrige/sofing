package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCertificationRepository extends JpaRepository<ProductCertification, Integer> {
}