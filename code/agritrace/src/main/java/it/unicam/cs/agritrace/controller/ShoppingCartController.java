package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.AddShCaPackage;
import it.unicam.cs.agritrace.dtos.requests.AddShCaProduct;
<<<<<<< Updated upstream
import it.unicam.cs.agritrace.dtos.requests.AddShCaPackage;
import it.unicam.cs.agritrace.dtos.requests.AddShCaProduct;
=======
>>>>>>> Stashed changes
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ACQUIRENTE')")
@RestController
@RequestMapping("/api/cart") // Path di base per tutte le operazioni sul carrello
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    // Aggiungi un prodotto singolo
    @PostMapping("/items/product")
    public ResponseEntity<String> addProductToCart(@RequestBody AddShCaProduct request) {
        shoppingCartService.addProductToCart(request);
        return ResponseEntity.ok("Prodotto aggiunto al carrello con successo.");
    }

    // Aggiungi un pacchetto
    @PostMapping("/items/package")
    public ResponseEntity<String> addPackageToCart(@RequestBody AddShCaPackage request) {
        shoppingCartService.addPackageToCart(request);
        return ResponseEntity.ok("Pacchetto aggiunto al carrello con successo.");
    }

    // Rimuovi un prodotto singolo
    @DeleteMapping("/items/product/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable int productId) {
        shoppingCartService.removeProductFromCart(productId);
        return ResponseEntity.noContent().build();
    }

    // Rimuovi un pacchetto
    @DeleteMapping("/items/package/{packageId}")
    public ResponseEntity<Void> removePackageFromCart(@PathVariable int packageId) {
        shoppingCartService.removePackageFromCart(packageId);
        return ResponseEntity.noContent().build();
    }

    // Recupera il carrello completo (prodotti + pacchetti)
    @GetMapping("/items")
    public ResponseEntity<ShoppingCartResponse> getShoppingCart() {
        return ResponseEntity.ok(shoppingCartService.getShoppingCart());
    }
}