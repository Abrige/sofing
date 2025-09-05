package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.requests.UpdateOrderStatusRequest;
import it.unicam.cs.agritrace.dtos.responses.OrderResponse;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer id){
        return ResponseEntity.ok(orderService.GetOrderById(id));
        /*
        {
    "status": 500,
    "error": "Unexpected server error at /api/orders/4",
    "timestamp": "2025-09-06T01:11:34.4606588",
    "details": "Type definiti
         */

    }


    // Aggiorna lo status di un ordine in base a quelli consentiti
    @PatchMapping("/update-status")
    public ResponseEntity<OrderDTO> updateStatus(@RequestBody @Valid UpdateOrderStatusRequest request) {
        // Ritorna l'ordine aggiornato
        return ResponseEntity.ok(orderService.updateOrderStatus(request));
    }

    // Mandando l'id nella URL crea dall'id di un carrello un ordine vero e proprio
    @PostMapping("/from-cart/{cartId}")
    public ResponseEntity<OrderDTO> createOrderFromCart(@PathVariable Integer cartId) {
        OrderDTO orderDTO = orderService.createOrderFromCart(cartId);
        // Risposta con l'ordine creato
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }
}
