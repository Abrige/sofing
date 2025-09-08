package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.requests.AddShCaPackage;
import it.unicam.cs.agritrace.dtos.requests.AddShCaProduct;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ACQUIRENTE')")
@RestController
@RequestMapping("/api/cart") // Path di base per tutte le operazioni sul carrello
@Tag(name = "Carrello", description = "Gestione del carrello acquisti (prodotti e pacchetti)")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    // Aggiungi un prodotto singolo
    @PostMapping("/items/product")
    @Operation(
            summary = "Aggiunge un prodotto al carrello",
            description = "Permette di inserire un prodotto singolo all'interno del carrello.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati del prodotto da aggiungere al carrello",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio aggiunta prodotto",
                                    value = """
                                    {
                                      "product_listing_id": 1,
                                      "quantity": 3
                                    }
                                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Prodotto aggiunto con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Categoria di prodotto non trovata")
    public ResponseEntity<String> addProductToCart(@RequestBody AddShCaProduct request) {
        shoppingCartService.addProductToCart(request);
        return ResponseEntity.ok("Prodotto aggiunto al carrello con successo.");
    }

    // Aggiungi un pacchetto
    @PostMapping("/items/package")
    @Operation(
            summary = "Aggiunge un pacchetto al carrello",
            description = "Permette di inserire un pacchetto di prodotti all'interno del carrello.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati del pacchetto da aggiungere al carrello",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio aggiunta pacchetto",
                                    value = """
                                    {
                                      "package_id": 4,
                                      "quantity": 1
                                    }
                                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Pacchetto aggiunto con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    public ResponseEntity<String> addPackageToCart(@RequestBody AddShCaPackage request) {
        shoppingCartService.addPackageToCart(request);
        return ResponseEntity.ok("Pacchetto aggiunto al carrello con successo.");
    }

    // Rimuovi un prodotto singolo
    @DeleteMapping("/items/product/{productId}")
    @Operation(
            summary = "Rimuove un prodotto dal carrello",
            description = "Permette di eliminare un prodotto singolo dal carrello tramite il suo ID."
    )
    @ApiResponse(responseCode = "204", description = "Prodotto rimosso con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Prodotto non trovato nel carrello")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable int productId) {
        shoppingCartService.removeProductFromCart(productId);
        return ResponseEntity.noContent().build();
    }

    // Rimuovi un pacchetto
    @DeleteMapping("/items/package/{packageId}")
    @Operation(
            summary = "Rimuove un pacchetto dal carrello",
            description = "Permette di eliminare un pacchetto dal carrello tramite il suo ID."
    )
    @ApiResponse(responseCode = "204", description = "Pacchetto rimosso con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Pacchetto non trovato nel carrello")
    public ResponseEntity<Void> removePackageFromCart(@PathVariable int packageId) {
        shoppingCartService.removePackageFromCart(packageId);
        return ResponseEntity.noContent().build();
    }

    // Recupera il carrello completo (prodotti + pacchetti)
    @GetMapping("/items")
    @Operation(
            summary = "Recupera il carrello",
            description = "Restituisce i dettagli del carrello, inclusi prodotti e pacchetti selezionati."
    )
    @ApiResponse(responseCode = "200", description = "Carrello recuperato con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    public ResponseEntity<ShoppingCartResponse> getShoppingCart() {
        return ResponseEntity.ok(shoppingCartService.getShoppingCart());
    }
}