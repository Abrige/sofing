package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
  List<User> findByRole(UserRole role);
  Optional<User> findByIdAndIsDeletedFalse(Integer id);
}