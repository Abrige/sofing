package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddRawMaterialPayload;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.service.ProductService;
import it.unicam.cs.agritrace.service.SupplyChainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/supplychain")
@Tag(name = "Filiera produttiva", description = "Gestione della filiera: prodotti, materie prime e ingredienti")
public class SupplyChainController {

    private final ProductService productService;
    private final SupplyChainService supplyChainService;

    public SupplyChainController(
                                 ProductService productService,
                                 SupplyChainService supplyChainService) {
        this.productService = productService;
        this.supplyChainService = supplyChainService;
    }

    @GetMapping("/getproducts")
    @Operation(
            summary = "Ottieni i prodotti di un'azienda",
            description = "Restituisce la lista di tutti i prodotti registrati da una specifica azienda identificata tramite companyId"
    )
    @ApiResponse(responseCode = "200", description = "Lista dei prodotti recuperata con successo")
    @ApiResponse(responseCode = "404", description = "Azienda non trovata")
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam int companyId) {
        List<ProductDTO> productDTOS = productService.getAllCompanyProductById(companyId);
        return ResponseEntity.ok(productDTOS);
    }

    @PostMapping("/products/{productId}/ingredients")
    @Operation(
            summary = "Aggiungi materie prime a un prodotto",
            description = "Permette di associare una o più materie prime (ingredienti) a un prodotto esistente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lista di materie prime da aggiungere al prodotto",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio materie prime",
                                    value = """
                    [
                      { "production_id": 1,
                       "step_type": "produzione",
                       "description": "Lattuga"
                       },
                      { "production_id": 2,
                       "step_type": "produzione",
                       "description": "Pomodori ROssi"
                       },
                       { "production_id": 15,
                       "step_type": "produzione",
                       "description": "Mozzarella"
                       }
                    ]
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Materie prime aggiunte con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
    public ResponseEntity<Void> addRawMaterial(@PathVariable int productId,
                                               @RequestBody List<AddRawMaterialPayload> payloads) {
        supplyChainService.addRawMaterials(productId, payloads);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{productId}/ingredients/{ingredientId}")
    @Operation(
            summary = "Rimuovi una materia prima da un prodotto",
            description = "Rimuove una materia prima associata a un prodotto tramite i rispettivi ID"
    )
    @ApiResponse(responseCode = "200", description = "Materia prima rimossa con successo")
    @ApiResponse(responseCode = "404", description = "Prodotto o materia prima non trovati")
    public ResponseEntity<Void> removeRawMaterial(@PathVariable int productId,
                                                  @PathVariable int ingredientId) {
        supplyChainService.removeRawMaterial(productId, ingredientId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/products/{productId}/ingredients")
    @Operation(
            summary = "Recupera materie prime di un prodotto",
            description = "Restituisce la lista delle materie prime (ingredienti) utilizzate per realizzare un prodotto"
    )
    @ApiResponse(responseCode = "200", description = "Lista materie prime recuperata con successo")
    @ApiResponse(responseCode = "404", description = "Prodotto non trovato")
    public ResponseEntity<List<ProductDTO>> getRawMaterialsByOutputProduct(@PathVariable int productId) {
        return ResponseEntity.ok(supplyChainService.getRawMaterialsByOutputProduct(productId));

    }
}
