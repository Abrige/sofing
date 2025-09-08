package it.unicam.cs.agritrace.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.responses.ProductCategoryResponse;
import it.unicam.cs.agritrace.service.ProductCategoryService;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
@RestController
@RequestMapping("/api/product-category")
@Tag(name = "Categorie di prodotto", description = "Gestione delle categorie di prodotto disponibili")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;


    public ProductCategoryController(ProductCategoryService productCategoryService, RequestService requestService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/list")
    @Operation(
            summary = "Recupera tutte le categorie di prodotto",
            description = "Ritorna la lista completa delle categorie di prodotto disponibili nel sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista categorie recuperata con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    public ResponseEntity<List<ProductCategoryResponse>> getAllProductCategory(){
        List<ProductCategoryResponse> productCategory = productCategoryService.getProductCategoryAll();

        return ResponseEntity.ok(productCategory);

    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera una categoria di prodotto per ID",
            description = "Restituisce i dettagli di una categoria di prodotto identificata dal suo ID."
    )
    @ApiResponse(responseCode = "200", description = "Categoria di prodotto recuperata con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Categoria di prodotto non trovata")
    public ResponseEntity<ProductCategoryResponse> getProductCategoryById(@PathVariable Integer id){
        ProductCategoryResponse productCategoryById = productCategoryService.getProductCategorybyId(id);
        return ResponseEntity.ok(productCategoryById);
    }
}
