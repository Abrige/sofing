package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}