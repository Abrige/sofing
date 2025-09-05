package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.AddToShoppingCartRequest;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-cart") // Path di base per tutte le operazioni sul carrello
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/items")
    public ResponseEntity<String> addProductToCart(@RequestBody AddToShoppingCartRequest request) {
        shoppingCartService.addProductToCart(request);
        return ResponseEntity.ok("Prodotto aggiunto al carrello con successo.");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable int productId) {
        shoppingCartService.removeProductFromCart(productId);
        // HTTP 204 No Content è lo standard per una cancellazione riuscita
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items")
    public ResponseEntity<ShoppingCartResponse> getShoppingCart() {
        return ResponseEntity.ok(shoppingCartService.getShoppingCart());
    }
}