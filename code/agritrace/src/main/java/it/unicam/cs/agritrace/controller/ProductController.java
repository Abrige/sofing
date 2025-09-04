package it.unicam.cs.agritrace.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST /api/products/{id}
    @GetMapping(value="/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) {
        ProductDTO product = productService.getProductById(id); // delega al service
        return ResponseEntity.ok(product);
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(){
        List<ProductDTO> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // –––––––––––––––––––––––––– CREATE PRODUCT ––––––––––––––––––––––––––
    // POST /api/products
    @PostMapping
    public ResponseEntity<OperationResponse> createProduct(
            @Valid @ValidProductCreate @RequestBody ProductPayload payload) {
        OperationResponse operationResponse = productService.createProductRequest(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationResponse);
    }

    // –––––––––––––––––––––––––– UPDATE PRODUCT ––––––––––––––––––––––––––
    // PUT /api/products
    @PutMapping
    public ResponseEntity<OperationResponse> updateProduct(
            @Valid @ValidProductUpdate @RequestBody ProductPayload productPayload) {

        OperationResponse operationResponse = productService.updateProductRequest(productPayload);
        return ResponseEntity.ok(operationResponse);
    }

    // –––––––––––––––––––––––––– DELETE PRODUCT ––––––––––––––––––––––––––
    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<OperationResponse> deleteProduct(@PathVariable int id) {
        OperationResponse operationResponse = productService.deleteProductRequest(new DeletePayload(id));
        // si ritorna questo stato in caso di delete andata a buon fine
        return ResponseEntity.ok(operationResponse);
    }

}
