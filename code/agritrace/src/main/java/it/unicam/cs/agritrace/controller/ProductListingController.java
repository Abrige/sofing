package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.common.ProductListingDTO;
import it.unicam.cs.agritrace.dtos.requests.AddProductToListing;
import it.unicam.cs.agritrace.dtos.responses.ProductListingResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.service.ProductListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listing")
@Tag(name = "Listino Prodotti", description = "Gestione del listino prodotti per le aziende")
public class ProductListingController {

    private final ProductListingService productListingService;
    private final CompanyRepository companyRepository;

    public ProductListingController(ProductListingService productListingService, CompanyRepository companyRepository) {
        this.productListingService = productListingService;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/products")
    @Operation(
            summary = "Aggiungi un prodotto al listino",
            description = "Permette di aggiungere un nuovo prodotto al listino di un'azienda",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dettagli del prodotto da aggiungere al listino",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio prodotto listino",
                                    value = """
                    {
                      "product_Id": 20,
                      "price": 12,
                      "quantity_available": 2,
                      "unit_of_measure": "kg"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Prodotto aggiunto al listino con successo")
    @ApiResponse(responseCode = "400", description = "Errore: prodotto non trovato o richiesta non valida")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")

    public ResponseEntity<String> addProductToListing(@RequestBody AddProductToListing productToList) {
        Company company = companyRepository.findById(1).orElseThrow();
        try{
            productListingService.addProductToListing(productToList, company);
            return ResponseEntity.ok("Prodotto aggiunto al listino con successo");
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Rimuove un prodotto di listino di un azienda
    @DeleteMapping("/{productId}/products/{companyId}")
    @Operation(
            summary = "Rimuovi un prodotto dal listino",
            description = "Permette di rimuovere un prodotto dal listino di un'azienda utilizzando l'ID del prodotto e dell'azienda"
    )
    @ApiResponse(responseCode = "204", description = "Prodotto rimosso dal listino con successo (No Content)")
    @ApiResponse(responseCode = "404", description = "Prodotto o azienda non trovati")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
    public ResponseEntity<?> removeProductToListing(@PathVariable int productId, @PathVariable int companyId) {
        //TODO uso la Company id nel mapping ma puo essere migliorata
        Company company = companyRepository.findById(companyId).orElseThrow();
        productListingService.removeProductToListing(productId, company);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    @Operation(
            summary = "Recupera tutti i prodotti del listino",
            description = "Ritorna la lista completa dei prodotti presenti nel listino"
    )
    @ApiResponse(responseCode = "200", description = "Lista prodotti recuperata con successo")
    @PreAuthorize("hasRole('ACQUIRENTE')")
    public ResponseEntity<List<ProductListingResponse>> getAllProducts() {
        List<ProductListingResponse> productListingResponses = productListingService.getAllListing();
        return ResponseEntity.ok(productListingResponses);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera un prodotto del listino per ID",
            description = "Ritorna le informazioni di un prodotto specifico presente nel listino tramite il suo ID"
    )
    @ApiResponse(responseCode = "200", description = "Prodotto del listino recuperato con successo")
    @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
    @PreAuthorize("hasRole('ACQUIRENTE')")
    public ResponseEntity<ProductListingResponse> getProductById(@PathVariable int id){
        ProductListingResponse productListingResponse = productListingService.getListingbyId(id);

        return ResponseEntity.ok(productListingResponse);
}


}
