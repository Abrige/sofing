package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Trova un ordine in base all'id dello status
    List<Order> findByStatus_Id(Integer id);
    // Trova un ordine in base al nome dello status
    List<Order> findByStatus_Name(String name);
}