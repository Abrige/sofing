package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestTypeRepository extends JpaRepository<RequestType, Integer> {
}