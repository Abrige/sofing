package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}