package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.UserCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCompanyRepository extends JpaRepository<UserCompany, Integer> {
}