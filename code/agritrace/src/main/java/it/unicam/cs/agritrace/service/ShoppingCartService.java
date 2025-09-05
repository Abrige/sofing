package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.requests.AddToShoppingCartRequest;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartItemResponse;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.ProductListing;
import it.unicam.cs.agritrace.model.ShoppingCart;
import it.unicam.cs.agritrace.model.ShoppingCartItem;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.ProductListingRepository;
import it.unicam.cs.agritrace.repository.ShoppingCartItemRepository;
import it.unicam.cs.agritrace.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductListingRepository productListingRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final UserService userService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductListingRepository productListingRepository,
                               ShoppingCartItemRepository shoppingCartItemRepository,
                               UserService userService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productListingRepository = productListingRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.userService = userService;
    }

    public ShoppingCartResponse getShoppingCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email dall’utente loggato

        User user = userService.getUserByEmail(email);

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
        List<ShoppingCartItemResponse> itemResponses = cart.getShoppingCartItems().stream()
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
    public void addProductToCart(AddToShoppingCartRequest request) {
        // 1. Trova il prodotto o lancia un'eccezione se non esiste
        ProductListing product = productListingRepository.findByProductIdAndIsActive(request.productId(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato"));

        // Recupera l'utente fittizio
        User user = userService.getUserById(1);

        // 2. Verifica la disponibilità (regola di business)
        if (product.getQuantityAvailable() < request.quantity()) {
            //TODO cambiare l'eccezione a una personalizzata
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
        Optional<ShoppingCartItem> existingItem = shoppingCartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {
            // Se esiste, aggiorna la quantità
            ShoppingCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
        } else {
            // Se non esiste, crea una nuova riga e aggiungila al carrello
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.quantity());
            cart.getShoppingCartItems().add(newItem);
        }

        // 5. Salva il carrello (e grazie a CascadeType.ALL, anche le sue righe)
        shoppingCartRepository.save(cart);
    }

    /**
     * Implementa il caso d'uso "Rimuovi Prodotto dal Carrello"
     */
    @Transactional
    public void removeProductFromCart(int productId) {
        // Recupera l'utente fittizio
        User user = userService.getUserById(1);

        // 1. Trova il carrello dell'utente
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Carrello non trovato"));

        // 2. Trova la riga da rimuovere
        ShoppingCartItem itemToRemove = cart.getShoppingCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non presente nel carrello"));

        // 3. Rimuovi la riga dalla lista. Grazie a orphanRemoval=true, verrà cancellata dal DB.
        cart.getShoppingCartItems().remove(itemToRemove);

        // 4. Salva il carrello aggiornato
        shoppingCartRepository.save(cart);
    }

    public ShoppingCart getCartById(int id){
        return shoppingCartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carrello con id=" + id + " non trovato"));
    }
}

