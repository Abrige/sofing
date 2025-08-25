package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}