package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.AddToShoppingCartRequest;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shoppingcart") // Path di base per tutte le operazioni sul carrello
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserRepository userRepository; // Per recuperare l'utente autenticato

    public ShoppingCartController(ShoppingCartService shoppingCartService, UserRepository userRepository) {
        this.shoppingCartService = shoppingCartService;
        this.userRepository = userRepository;
    }

    @PostMapping("/items")
    public ResponseEntity<?> addProductToCart(@RequestBody AddToShoppingCartRequest request) {
        // Recupera l'utente fittizio
        User currentUser = userRepository.findById(1).orElseThrow(/*...*/);

        try {
            shoppingCartService.addProductToCart(currentUser, request.productId(), request.quantity());
            return ResponseEntity.ok("Prodotto aggiunto al carrello con successo.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable int productId) {
        // Recupera l'utente fittizio
        User currentUser = userRepository.findById(1).orElseThrow(/*...*/);
        try {
            shoppingCartService.removeProductFromCart(currentUser, productId);
            // HTTP 204 No Content è lo standard per una cancellazione riuscita
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/items")
    public ResponseEntity<ShoppingCartResponse> getShoppingCart() {
        User currentUser = userRepository.findById(1).orElseThrow();
        return ResponseEntity.ok(shoppingCartService.getShoppingCart(currentUser));
    }
}