package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}