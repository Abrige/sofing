package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.DbTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface DbTableRepository extends JpaRepository<DbTable, Integer> {
}
