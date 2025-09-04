package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestTypeRepository extends JpaRepository<RequestType, Integer> {
    Optional<RequestType> findByName(String name);
}