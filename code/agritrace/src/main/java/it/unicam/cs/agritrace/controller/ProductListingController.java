package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.ProductListingDTO;
import it.unicam.cs.agritrace.dtos.requests.AddProductToListing;
import it.unicam.cs.agritrace.dtos.responses.ProductListingResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.service.ProductListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        productListingService.removeProductToListing(productId, company);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductListingResponse>> getAllProducts() {
        List<ProductListingResponse> productListingResponses = productListingService.getAllListing();
        return ResponseEntity.ok(productListingResponses);
    }
@GetMapping("/{id}")
    public ResponseEntity<ProductListingResponse> getProductById(@PathVariable int id){
        ProductListingResponse productListingResponse = productListingService.getListingbyId(id);

        return ResponseEntity.ok(productListingResponse);
}


}
