package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.payloads.ProductPayload;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.service.ProductService;
import it.unicam.cs.agritrace.validators.create.ValidProductCreate;
import it.unicam.cs.agritrace.validators.update.ValidProductUpdate;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/products")
@Tag(name = "Prodotti", description = "Gestione dei prodotti nel sistema")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // –––––––––––––––––––––––––– Get Product By Id ––––––––––––––––––––––––––
    // GET /api/products/{id}
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    @GetMapping(value="/{id}")
    @Operation(
            summary = "Recupera prodotto per ID",
            description = "Ritorna i dettagli di un prodotto dato il suo ID"
    )
    @ApiResponse(responseCode = "200", description = "Prodotto recuperato con successo")
    @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) {
        ProductDTO product = productService.getProductById(id); // delega al service
        return ResponseEntity.ok(product);
    }

    // –––––––––––––––––––––––––– Get All Products ––––––––––––––––––––––––––
    // GET /api/products
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    @GetMapping
    @Operation(
            summary = "Lista prodotti",
            description = "Ritorna tutti i prodotti registrati nel sistema"
    )
    @ApiResponse(responseCode = "200", description = "Prodotti recuperati con successo")
    @ApiResponse(responseCode = "204", description = "Nessun prodotto disponibile")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        List<ProductDTO> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // –––––––––––––––––––––––––– CREATE PRODUCT ––––––––––––––––––––––––––
    // POST /api/products
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
    @PostMapping
    @Operation(
            summary = "Crea un nuovo prodotto",
            description = "Crea un prodotto nel sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati del prodotto da creare",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Prodotto esempio",
                                    summary = "Esempio di creazione prodotto",
                                    value = """
                    {
                      "name": "Pomodoro senza nichel",
                      "description": "Pomodoro sicuro per allergie al nichel",
                      "category_Id": 1,
                      "cultivation_method_id": 1,
                      "harvest_season_id": 1,
                      "producer_id": 2
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Prodotto creato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<OperationResponse> createProduct(
            @Valid @ValidProductCreate @RequestBody ProductPayload payload) {
        OperationResponse operationResponse = productService.createProductRequest(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationResponse);
    }

    // –––––––––––––––––––––––––– UPDATE PRODUCT ––––––––––––––––––––––––––
    // PUT /api/products
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
    @PutMapping
    @Operation(
            summary = "Aggiorna un prodotto esistente",
            description = "Aggiorna i dati di un prodotto già registrato",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati del prodotto da aggiornare",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Aggiorna prodotto esempio",
                                    summary = "Esempio di aggiornamento prodotto",
                                    value = """
                    {
                      "id": 1,
                      "name": "Pistacchio pistacchioso",
                      "description": "Avete ragione non è di bronte"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Prodotto aggiornato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<OperationResponse> updateProduct(
            @Valid @ValidProductUpdate @RequestBody ProductPayload productPayload) {

        OperationResponse operationResponse = productService.updateProductRequest(productPayload);
        return ResponseEntity.ok(operationResponse);
    }

    // –––––––––––––––––––––––––– DELETE PRODUCT ––––––––––––––––––––––––––
    // DELETE /api/products/{id}
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Elimina un prodotto",
            description = "Elimina un prodotto dato il suo ID"
    )
    @ApiResponse(responseCode = "200", description = "Prodotto eliminato con successo")
    @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
    public ResponseEntity<OperationResponse> deleteProduct(@PathVariable int id) {
        OperationResponse operationResponse = productService.deleteProductRequest(new DeletePayload(id));
        // si ritorna questo stato in caso di delete andata a buon fine
        return ResponseEntity.ok(operationResponse);
    }

}
