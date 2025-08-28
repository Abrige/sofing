package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.mappers.OrderMapper;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Recupera tutti gli ordini e li mappa in DTO
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDto)  // usa il mapper
                .collect(Collectors.toList());
    }

    // Recupera un ordine per id e lo mappa
    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato con id " + id));
        return OrderMapper.toDto(order);
    }

    public List<OrderDTO> getOrdersByStatus(StatusType status) {
        return orderRepository.findByStatus_Id(status.getId())
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

}
