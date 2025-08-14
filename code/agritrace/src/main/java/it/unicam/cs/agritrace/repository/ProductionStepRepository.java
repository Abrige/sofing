package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductionStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionStepRepository extends JpaRepository<ProductionStep, Integer> {
}