package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);
  List<User> findByRole(UserRole role);
}