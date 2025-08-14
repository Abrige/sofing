package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.CompanyCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyCertificationRepository extends JpaRepository<CompanyCertification, Integer> {
}