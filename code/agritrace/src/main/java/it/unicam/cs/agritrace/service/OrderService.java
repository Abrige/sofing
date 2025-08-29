package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.mappers.OrderMapper;
import it.unicam.cs.agritrace.mappers.StatusMapper;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.model.Status;
import it.unicam.cs.agritrace.repository.OrderRepository;
import it.unicam.cs.agritrace.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;

    public OrderService(OrderRepository orderRepository,
                        StatusRepository statusRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
    }

    // Recupera tutti gli ordini e li mappa in DTO
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDto)  // usa il mapper
                .collect(Collectors.toList());
    }

    // Recupera un ordine per id e lo mappa a DTO
    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato con id " + id));
        return OrderMapper.toDto(order);
    }

    // Recupera tutti gli ordini in base allo status e li mappa a DTO
    public List<OrderDTO> getOrdersByStatus(StatusType status) {
        return orderRepository.findByStatus_Id(status.getId())
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public OrderDTO updateOrderStatus(int orderId, String statusName) {
        // Recupero ordine
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato con id: " + orderId));

        // Conversione della stringa in enum
        StatusType statusEnum = StatusType.fromNameIgnoreCase(statusName);

        // Scelta azione in base allo status
        switch (statusEnum) {
            case PENDING -> {
                return OrderMapper.toDto(acceptOrder(order));
            }
            case REJECTED -> {
                return OrderMapper.toDto(rejectOrder(order));
            }
            default -> throw new RuntimeException("Aggiornamento dello status non supportato: " + statusEnum);
        }
    }

    // Imposta lo stato dell'ordine a pending
    private Order acceptOrder(Order order) {
        Status status = statusRepository.findById(StatusType.PENDING.getId()).orElseThrow(() -> new RuntimeException("Status non trovato"));
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }

    // Imposta lo stato dell'ordine a rifiutato
    private Order rejectOrder(Order order) {
        Status status = statusRepository.findById(StatusType.REJECTED.getId()).orElseThrow(() -> new RuntimeException("Status non trovato"));
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }

}
