package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.RequestAddProduct;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.ProductService;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {

    private ProductService productService;
    private RequestService requestService;
    private UserRepository userRepository;

    @PostMapping("/createproduct")
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

    @DeleteMapping("/{id}")
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
