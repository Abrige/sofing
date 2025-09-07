package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.requests.AddShCaPackage;
import it.unicam.cs.agritrace.dtos.requests.AddShCaProduct;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartItemResponse;
import it.unicam.cs.agritrace.dtos.responses.ShoppingCartResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.ProductListingRepository;
import it.unicam.cs.agritrace.repository.ShoppingCartItemRepository;
import it.unicam.cs.agritrace.repository.ShoppingCartRepository;
import it.unicam.cs.agritrace.repository.TypicalPackageRepository;
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
    private final TypicalPackageRepository typicalPackageRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductListingRepository productListingRepository,
                               ShoppingCartItemRepository shoppingCartItemRepository,
                               UserService userService,
                               TypicalPackageRepository typicalPackageRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productListingRepository = productListingRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.userService = userService;
        this.typicalPackageRepository = typicalPackageRepository;
    }

    public ShoppingCartResponse getShoppingCart() {
        User user = getAuthenticatedUser();

        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElse(null);

        if (cart == null || cart.getShoppingCartItems().isEmpty()) {
            return new ShoppingCartResponse(Collections.emptyList(), 0.0);
        }

        List<ShoppingCartItemResponse> itemResponses = cart.getShoppingCartItems().stream()
                .map(item -> {
                    if (item.getProductListing() != null) {
                        double price = item.getProductListing().getPrice().doubleValue();
                        return new ShoppingCartItemResponse(
                                item.getProductListing().getId(),
                                null,
                                item.getProductListing().getProduct().getName(),
                                item.getQuantity(),
                                price,
                                price * item.getQuantity(),
                                "PRODUCT"
                        );
                    } else if (item.getTypicalPackage() != null) {
                        double price = item.getTypicalPackage().getPrice().doubleValue();
                        return new ShoppingCartItemResponse(
                                null,
                                item.getTypicalPackage().getId(),
                                item.getTypicalPackage().getName(),
                                item.getQuantity(),
                                price,
                                price * item.getQuantity(),
                                "PACKAGE"
                        );
                    } else {
                        throw new IllegalStateException("ShoppingCartItem senza prodotto né pacchetto!");
                    }
                })
                .toList();

        double total = itemResponses.stream()
                .mapToDouble(ShoppingCartItemResponse::subtotal)
                .sum();

        return new ShoppingCartResponse(itemResponses, total);
    }

    /**
     * Implementa il caso d'uso "Aggiungi Prodotto al Carrello"
     */
    @Transactional
    public void addProductToCart(AddShCaProduct request) {
        // 1. Trova il prodotto attivo o lancia eccezione
        ProductListing product = productListingRepository
                .findByProductIdAndIsActiveTrue(request.productListingId())
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato"));

        // 2. Recupera l'utente loggato
        User user = getAuthenticatedUser();

        // 3. Verifica disponibilità
        if (product.getQuantityAvailable() < request.quantity()) {
            throw new IllegalArgumentException("Quantità non disponibile in magazzino");
        }

        // 4. Trova o crea il carrello
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUser(user);
                    return shoppingCartRepository.save(newCart);
                });

        // 5. Controlla se il prodotto è già nel carrello
        Optional<ShoppingCartItem> existingItem = shoppingCartItemRepository
                .findByCartAndProductListing(cart, product);

        if (existingItem.isPresent()) {
            // Aggiorna quantità esistente
            ShoppingCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
        } else {
            // Crea nuovo item nel carrello
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setCart(cart);
            newItem.setProductListing(product);
            newItem.setTypicalPackage(null);
            newItem.setQuantity(request.quantity());
            cart.getShoppingCartItems().add(newItem);
        }

        // 6. Salva il carrello
        shoppingCartRepository.save(cart);
    }

    public void addPackageToCart(AddShCaPackage request) {
        // 1. Trova il pacchetto o lancia un'eccezione se non esiste
        TypicalPackage typicalPackage = typicalPackageRepository.findByIdAndIsActiveTrue(request.packageId())
                .orElseThrow(() -> new ResourceNotFoundException("Pacchetto non trovato"));

        // 2. Recupera l'utente loggato
        User user = getAuthenticatedUser();

        // 3. Trova il carrello dell'utente. Se non esiste, crealo
        ShoppingCart cart = shoppingCartRepository.findByUser(user).orElseGet(() -> {
            ShoppingCart newCart = new ShoppingCart();
            newCart.setUser(user);
            return shoppingCartRepository.save(newCart);
        });

        // 4. Controlla se il pacchetto è già nel carrello
        Optional<ShoppingCartItem> existingItem = shoppingCartItemRepository
                .findByCartAndTypicalPackage(cart, typicalPackage);

        if (existingItem.isPresent()) {
            // Se esiste, aggiorna la quantità
            ShoppingCartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
        } else {
            // Se non esiste, crea una nuova riga
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setCart(cart);
            newItem.setTypicalPackage(typicalPackage); // solo questo campo, PRODUCT_LISTING_ID resta null
            newItem.setQuantity(request.quantity());
            cart.getShoppingCartItems().add(newItem);
        }

        // 5. Salva il carrello
        shoppingCartRepository.save(cart);
    }

    /**
     * Implementa il caso d'uso "Rimuovi Prodotto dal Carrello"
     */
    @Transactional
    public void removeProductFromCart(int productListingId) {
        User user = getAuthenticatedUser();

        // Trova il carrello dell'utente
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Carrello non trovato"));

        // Trova l'item corrispondente al prodotto
        ShoppingCartItem itemToRemove = cart.getShoppingCartItems().stream()
                .filter(item -> item.getProductListing() != null
                        && item.getProductListing().getId().equals(productListingId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non presente nel carrello"));

        // Rimuovi l'item (orphanRemoval = true cancella dal DB)
        cart.getShoppingCartItems().remove(itemToRemove);

        shoppingCartRepository.save(cart);
    }

    /**
     * Implementa il caso d'uso "Rimuovi Prodotto dal Carrello"
     */
    @Transactional
    public void removePackageFromCart(int packageId) {
        User user = getAuthenticatedUser();

        // Trova il carrello dell'utente
        ShoppingCart cart = shoppingCartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Carrello non trovato"));

        // Trova l'item corrispondente al pacchetto
        ShoppingCartItem itemToRemove = cart.getShoppingCartItems().stream()
                .filter(item -> item.getTypicalPackage() != null
                        && item.getTypicalPackage().getId().equals(packageId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Pacchetto non presente nel carrello"));

        // Rimuovi l'item
        cart.getShoppingCartItems().remove(itemToRemove);

        shoppingCartRepository.save(cart);
    }

    public ShoppingCart getCartById(int id){
        return shoppingCartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carrello con id=" + id + " non trovato"));
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }
}

