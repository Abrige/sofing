package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.ProductDTO;
import it.unicam.cs.agritrace.dtos.requests.RequestAddProduct;
import it.unicam.cs.agritrace.exceptions.ProductNotFoundException;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.ProductService;
import it.unicam.cs.agritrace.service.RequestService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private ProductService productService;
    private RequestService requestService;
    private UserRepository userRepository;

    public ProductController(ProductRepository productRepository, ProductService productService, RequestService requestService, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.productService = productService;
        this.requestService = requestService;
        this.userRepository = userRepository;
    }

    @GetMapping(value="/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable int id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .map(product -> new ResponseEntity<>(new ProductDTO(product), HttpStatus.OK))
                .orElseThrow(ProductNotFoundException::new);
    }

    @GetMapping(value="/products")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        List<ProductDTO> prodotti = productRepository.findAll()
                .stream()
                .map(ProductDTO::new)
                .toList();
        if (prodotti.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(prodotti, HttpStatus.OK);
    }

    @PostMapping("/create-product")
    public ResponseEntity<?> addProduct(@RequestBody RequestAddProduct requestAddProduct) {
        try {
            // 🔹 Recupero un utente fittizio con id=1
            Optional<User> maybeUser = userRepository.findById(1);

            if (maybeUser.isEmpty()) {
                // Se non esiste, restituisco un 404 esplicito
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Fake user with id=1 not found. Insert it in DB first.");
            }

            User fakeUser = maybeUser.get();

            // 🔹 Creo la request
            Request request = requestService.createProductRequest(fakeUser, requestAddProduct);

            // 🔹 Restituisco il risultato con 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception e) {
            // 🔹 Catch generico per evitare 500 non gestiti
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la creazione del product request: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la cancellazione del prodotto: " + e.getMessage());
        }
    }



}
