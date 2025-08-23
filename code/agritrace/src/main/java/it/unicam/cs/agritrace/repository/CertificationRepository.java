package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Integer> {
  Optional<Certification> findByName(String name);
}