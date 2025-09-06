package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}