package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.requests.UpdateOrderStatusRequest;
import it.unicam.cs.agritrace.dtos.responses.OrderResponse;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Ordini", description = "Gestione degli ordini nel sistema") // Gruppo in Swagger UI
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Restituisce tutti gli ordini, con filtro opzionale per stato.
     */
    @GetMapping
    @Operation(
            summary = "Recupera tutti gli ordini",
            description = "Ritorna la lista di tutti gli ordini. Se specificato, è possibile filtrare per stato."
    )
    @ApiResponse(responseCode = "200", description = "Lista ordini recuperata con successo")
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


    /**
     * Restituisce un ordine specifico dato l'ID.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera un ordine per ID",
            description = "Restituisce i dettagli di un ordine dato il suo identificativo."
    )
    @ApiResponse(responseCode = "200", description = "Ordine recuperato con successo")
    @ApiResponse(responseCode = "404", description = "Ordine non trovato")
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

    /**
     * Aggiorna lo stato di un ordine. Richiede un ruolo autorizzato.
     */
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE')")
    @PatchMapping("/update-status")
    @Operation(
            summary = "Aggiorna lo stato di un ordine",
            description = "Permette di aggiornare lo stato di un ordine tra quelli consentiti. " +
                    "Richiede uno dei ruoli: PRODUTTORE, TRASFORMATORE, DISTRIBUTORE."
    )
    @ApiResponse(responseCode = "200", description = "Ordine aggiornato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<OrderDTO> updateStatus(@RequestBody @Valid UpdateOrderStatusRequest request) {
        // Ritorna l'ordine aggiornato
        return ResponseEntity.ok(orderService.updateOrderStatus(request));
    }

    /**
     * Crea un ordine a partire dal contenuto di un carrello esistente.
     */
    @PostMapping("/from-cart/{cartId}")
    @Operation(
            summary = "Crea un ordine da un carrello",
            description = "Genera un nuovo ordine a partire dal contenuto di un carrello esistente, identificato dal suo ID."
    )
    @ApiResponse(responseCode = "201", description = "Ordine creato con successo")
    @ApiResponse(responseCode = "404", description = "Carrello non trovato")
    public ResponseEntity<OrderDTO> createOrderFromCart(@PathVariable Integer cartId) {
        OrderDTO orderDTO = orderService.createOrderFromCart(cartId);
        // Risposta con l'ordine creato
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }
}
