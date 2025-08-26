package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.ProductDTO;
import it.unicam.cs.agritrace.dtos.requests.RequestAddProduct;
import it.unicam.cs.agritrace.dtos.responses.ProductRequestResponse;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.service.ProductService;
import it.unicam.cs.agritrace.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final RequestService requestService;

    public ProductController(ProductService productService,
                             RequestService requestService) {
        this.productService = productService;
        this.requestService = requestService;
    }

    // POST /api/products/{id}
    @GetMapping(value="/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) {
        log.info("Fetching product with id={}", id);
        ProductDTO product = productService.getProductById(id); // delega al service
        return ResponseEntity.ok(product);
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(){
        log.info("Fetching all products");
        List<ProductDTO> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<ProductRequestResponse> addProduct(@RequestBody RequestAddProduct requestAddProduct) {
        log.info("Creating new product request: {}", requestAddProduct);

        Request created = requestService.createProductRequest(requestAddProduct);

        // DTO di ritorno
        ProductRequestResponse response = new ProductRequestResponse(
                created.getId(),
                created.getStatus().getName(),
                created.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        log.info("Deleting product with id={}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
