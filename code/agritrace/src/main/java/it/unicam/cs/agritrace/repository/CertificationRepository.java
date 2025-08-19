package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Integer> {
}