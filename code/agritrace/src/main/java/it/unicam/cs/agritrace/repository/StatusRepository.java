package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
  Optional<Status> findByName(String name);
  Optional<Status> findByNameIgnoreCase(String name);
}