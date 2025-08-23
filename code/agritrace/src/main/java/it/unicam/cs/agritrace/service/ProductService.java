package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void deleteProduct(int productId) {
        // 1. Trova il prodotto nel database
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Prodotto non trovato con id: " + productId));

//        // 2. Logica di Sicurezza e Autorizzazione
//        String userRole = currentUser.getRole();
//        long productOwnerId = product.getUser().getId(); // Assumendo che Product abbia una relazione con User
//
//        // L'utente può cancellare SE:
//        // - È un Curatore/Admin
//        // - OPPURE è il proprietario del prodotto
//        if (!userRole.equals("CURATORE") && !userRole.equals("ADMIN") && productOwnerId != currentUser.getId()) {
//            throw new UnauthorizedOperationException("L'utente non ha i permessi per cancellare questo prodotto.");
//        }

        // 3. Esegui il Soft Delete
        product.setIsDeleted(true);

        // 4. Salva le modifiche nel database
        productRepository.save(product);
    }
}

// Nota: ResourceNotFoundException e UnauthorizedOperationException sono eccezioni custom che dovresti creare.
// Esempio: public class ResourceNotFoundException extends RuntimeException { ... }

