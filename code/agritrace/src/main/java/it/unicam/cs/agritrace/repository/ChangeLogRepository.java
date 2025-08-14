package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, Integer> {
}