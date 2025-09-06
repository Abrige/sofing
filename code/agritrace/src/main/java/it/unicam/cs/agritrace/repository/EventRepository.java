package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByIsActiveTrue( );
    Optional<Event> findByIdAndIsActiveTrue(Integer id);
}