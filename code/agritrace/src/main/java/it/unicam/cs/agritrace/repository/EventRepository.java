package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}