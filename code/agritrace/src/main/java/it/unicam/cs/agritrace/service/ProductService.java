package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.ProductDTO;
import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.exceptions.ProductNotFoundException;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.mappers.RequestMapper;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id=" + id));
        return productMapper.toDto(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDtoList(products); // usa il mapper
    }

    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id=" + id);
        }
        // TODO fare soft delete del prodotto (se un prodotto in soft delete esiste già si deve riattivare quello invece che crearne uno nuovo)
    }
/*
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

 */
}

// Nota: ResourceNotFoundException e UnauthorizedOperationException sono eccezioni custom che dovresti creare.
// Esempio: public class ResourceNotFoundException extends RuntimeException { ... }

