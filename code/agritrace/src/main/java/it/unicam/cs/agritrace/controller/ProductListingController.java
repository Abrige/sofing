package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.AddProductToListing;
import it.unicam.cs.agritrace.dtos.responses.ProductListingResponse;
import it.unicam.cs.agritrace.exceptions.ProductNotFoundException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductListing;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.service.ProductListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/listing")
public class ProductListingController {

    private final ProductRepository productRepository;
    private final ProductListingService productListingService;
    private final CompanyRepository companyRepository;

    public ProductListingController(ProductRepository productRepository, ProductListingService productListingService, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.productListingService = productListingService;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProductToListing(@RequestBody AddProductToListing productToList) {
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
    public ResponseEntity<?> removeProductToListing(@PathVariable int productId, @PathVariable int companyId) {
        //TODO uso la Company id nel mapping ma puo essere migliorata
        Company company = companyRepository.findById(companyId).orElseThrow();
        try {
            productListingService.removeProductToListing(productId, company);
            return ResponseEntity.noContent().build();
        }catch (ProductNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductListingResponse>> getAllProducts() {
        List<ProductListingResponse> productListingResponses = productListingService.getAllListing();
        return ResponseEntity.ok(productListingResponses);
    }

}
