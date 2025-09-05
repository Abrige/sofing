package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
  Optional <UserRole> findByNameAndIsActiveTrue(String name);
}