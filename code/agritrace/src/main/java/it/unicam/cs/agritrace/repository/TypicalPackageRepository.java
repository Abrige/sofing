package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.TypicalPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypicalPackageRepository extends JpaRepository<TypicalPackage, Integer> {
    Optional<TypicalPackage> findByIdAndIsActiveTrue(Integer id);
}