package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsernameAndIsDeletedFalse(String username);
  Optional<User> findByIdAndIsDeletedFalse(Integer id);
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String username);
  Optional<User> findByEmailAndIsDeletedFalse(String email);
}