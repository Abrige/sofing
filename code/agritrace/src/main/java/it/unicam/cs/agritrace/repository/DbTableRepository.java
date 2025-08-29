package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.DbTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DbTableRepository extends JpaRepository<DbTable, Integer> {
    Optional<DbTable> findDbTableByName(String name);
}
