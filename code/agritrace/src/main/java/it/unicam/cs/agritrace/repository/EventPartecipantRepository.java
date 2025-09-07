package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.EventPartecipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPartecipantRepository extends JpaRepository<EventPartecipant, Integer> {
}