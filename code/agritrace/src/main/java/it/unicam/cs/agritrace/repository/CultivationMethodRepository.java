package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.CultivationMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CultivationMethodRepository extends JpaRepository<CultivationMethod, Integer> {
    Optional<CultivationMethod> findByIdAndIsActiveTrue(Integer id);
}