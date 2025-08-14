package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}