package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.TypicalPackageItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypicalPackageItemRepository extends JpaRepository<TypicalPackageItem, Integer> {
}