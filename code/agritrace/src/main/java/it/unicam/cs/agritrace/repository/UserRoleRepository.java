package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

  List<UserRole> findByIsActiveTrue();
  UserRole findByName(String name);
}