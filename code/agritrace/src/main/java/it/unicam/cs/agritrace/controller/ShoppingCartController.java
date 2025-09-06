package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.requests.AddToShoppingCartRequest;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping-cart") // Path di base per tutte le operazioni sul carrello
@Tag(name = "Carrello", description = "Operazioni sul carrello della spesa")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/items")
    @Operation(
            summary = "Aggiungi prodotto al carrello",
            description = "Permette di aggiungere un prodotto al carrello specificando l'ID del prodotto e la quantità",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati per l'aggiunta di un prodotto al carrello",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio aggiunta prodotto",
                                    value = """
                    {
                      "product_Id": 1,
                      "quantity": 2
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Prodotto aggiunto al carrello con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<String> addProductToCart(@RequestBody AddToShoppingCartRequest request) {
        shoppingCartService.addProductToCart(request);
        return ResponseEntity.ok("Prodotto aggiunto al carrello con successo.");
    }

    @DeleteMapping("/items/{productId}")
    @Operation(
            summary = "Rimuovi prodotto dal carrello",
            description = "Rimuove un prodotto dal carrello utilizzando l'ID del prodotto"
    )
    @ApiResponse(responseCode = "204", description = "Prodotto rimosso con successo (No Content)")
    @ApiResponse(responseCode = "404", description = "Prodotto non trovato nel carrello")
    public ResponseEntity<?> removeProductFromCart(@PathVariable int productId) {
        shoppingCartService.removeProductFromCart(productId);
        // HTTP 204 No Content è lo standard per una cancellazione riuscita
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items")
    @Operation(
            summary = "Recupera carrello",
            description = "Ritorna il contenuto attuale del carrello dell'utente"
    )
    @ApiResponse(responseCode = "200", description = "Carrello recuperato con successo")
    public ResponseEntity<ShoppingCartResponse> getShoppingCart() {
        return ResponseEntity.ok(shoppingCartService.getShoppingCart());
    }
}