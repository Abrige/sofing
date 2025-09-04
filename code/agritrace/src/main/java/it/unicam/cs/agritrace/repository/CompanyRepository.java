package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByIdAndIsDeletedFalse(Integer id);
}