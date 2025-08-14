package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}