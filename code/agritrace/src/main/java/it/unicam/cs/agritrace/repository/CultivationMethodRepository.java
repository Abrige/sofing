package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.CultivationMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CultivationMethodRepository extends JpaRepository<CultivationMethod, Integer> {
}