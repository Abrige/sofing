package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.requests.CreateOrder;
import it.unicam.cs.agritrace.dtos.requests.UpdateOrderStatusRequest;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Ritorna tutti gli ordini (in base anche allo opzionale stato in cui si trovano)
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(
            @RequestParam(name = "status", required = false) String statusParam) {
        List<OrderDTO> orders;

        if (statusParam != null) {
            // case-insensitive
            StatusType statusType = StatusType.fromNameIgnoreCase(statusParam);
            orders = orderService.getOrdersByStatus(statusType);
        } else {
            orders = orderService.getAllOrders();
        }
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/updatestatus")
    public ResponseEntity<OrderDTO> updateStatus(@RequestBody UpdateOrderStatusRequest request) {
        OrderDTO orderDTO = orderService.updateOrderStatus(request.orderId(), request.status());
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody @Valid CreateOrder request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.noContent().build();
    }
}
