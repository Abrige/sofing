package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dto.ProductRequestDto;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class ProductController {

    private RequestService requestService;
    private UserRepository userRepository;

    public ProductController(RequestService requestService,
                             UserRepository userRepository) {
        this.requestService = requestService;
        this.userRepository = userRepository;
    }

    @PostMapping("/addproduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequestDto productRequestDto) {
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
            Request request = requestService.createProductRequest(fakeUser, productRequestDto);

            // 🔹 Restituisco il risultato con 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception e) {
            // 🔹 Catch generico per evitare 500 non gestiti
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la creazione del product request: " + e.getMessage());
        }
    }

}
