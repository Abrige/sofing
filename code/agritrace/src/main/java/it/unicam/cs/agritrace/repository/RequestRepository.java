package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {

  @Query("SELECT r FROM Request r WHERE r.status.id = 2")
  List<Request> findByStatusIsPending();

  // Trova un ordine in base all'id dello status
  List<Request> findByStatus_Id(Integer id);
}
