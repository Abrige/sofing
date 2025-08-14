package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.TypicalPackagesItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypicalPackagesItemRepository extends JpaRepository<TypicalPackagesItem, Integer> {
}