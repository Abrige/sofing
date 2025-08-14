package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.DbTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbTableRepository extends JpaRepository<DbTable, Integer> {
}