package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}