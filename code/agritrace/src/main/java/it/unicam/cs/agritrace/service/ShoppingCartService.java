package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.responses.ShoppingCartItemResponse;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.ProductListingRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.ShoppingCartItemRepository;
import it.unicam.cs.agritrace.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartItemRepository cartItemRepository;
    private final ProductListingRepository productListingRepository;

    // Constructor Injection (best practice)
    public ShoppingCartService(ShoppingCartRepository cartRepository, ProductRepository productRepository, ShoppingCartItemRepository cartItemRepository,
                               ProductListingRepository productListingRepository, ShoppingCartRepository shoppingCartRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.productListingRepository = productListingRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCartResponse getShoppingCart(User user) {
        // 1. Trova il carrello dell'utente dal repository.
        Optional<ShoppingCart> maybeCart = shoppingCartRepository.findByUser(user);

        // 2. Gestisci il caso in cui il carrello non esista.
        if (maybeCart.isEmpty()) {
            // Restituisci un carrello vuoto.
            return new ShoppingCartResponse(Collections.emptyList(), 0.0);
        }

        // 3. Se il carrello esiste, procedi con la mappatura.
        ShoppingCart cart = maybeCart.get();

        // 4. Trasforma ogni ShoppingCartItem (Entity) in un CartItemResponse (DTO).
        List<ShoppingCartItemResponse> itemResponses = cart.getItems().stream()
                .map(item -> new ShoppingCartItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice().intValue(),
                        item.getQuantity() * item.getProduct().getPrice().intValue() // Calcola il subtotale
                ))
                .toList(); // Usa .collect(Collectors.toList()) per versioni Java più vecchie

        // 5. Calcola il totale del carrello.
        double total = itemResponses.stream()
                .mapToDouble(ShoppingCartItemResponse::subtotal)
                .sum();

        // 6. Crea e restituisci il DTO finale.
        return new ShoppingCartResponse(itemResponses, total);
    }

    /**
     * Implementa il caso d'uso "Aggiungi Prodotto al Carrello"
     */
    @Transactional
    public void addProductToCart(User user, int productId, int quantity) {
        // 1. Trova il prodotto o lancia un'eccezione se non esiste
        ProductListing product = productListingRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato"));

        // 2. Verifica la disponibilità (regola di business)
        if (product.getQuantityAvailable() < quantity) {
            //TODO cambirare l'eccezione a una personalizzata
            throw new IllegalArgumentException("Quantità non disponibile in magazzino");
        }

        // 3. Trova il carrello dell'utente. Se non esiste, crealo.
        ShoppingCart cart = shoppingCartRepository.findByUser(user).orElseGet(() -> {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            // Salva subito il nuovo carrello per renderlo persistente e ottenere un ID.
            return shoppingCartRepository.save(newCart);
        });

        // 4. Controlla se il prodotto è già nel carrello per decidere se aggiornare o creare
        Optional<ShoppingCartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {
            // Se esiste, aggiorna la quantità
            ShoppingCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            // Se non esiste, crea una nuova riga e aggiungila al carrello
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        // 5. Salva il carrello (e grazie a CascadeType.ALL, anche le sue righe)
        shoppingCartRepository.save(cart);
    }

    /**
     * Implementa il caso d'uso "Rimuovi Prodotto dal Carrello"
     */
    @Transactional
    public void removeProductFromCart(User user, int productId) {
        // 1. Trova il carrello dell'utente
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Carrello non trovato"));

        // 2. Trova la riga da rimuovere
        ShoppingCartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non presente nel carrello"));

        // 3. Rimuovi la riga dalla lista. Grazie a orphanRemoval=true, verrà cancellata dal DB.
        cart.getItems().remove(itemToRemove);

        // 4. Salva il carrello aggiornato
        shoppingCartRepository.save(cart);
    }
}

