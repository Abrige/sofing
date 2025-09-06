package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Integer> {
}