package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductListing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductListingRepository extends JpaRepository<ProductListing, Integer> {
    Optional<ProductListing> findByProductId(Integer productId);

    //Trova il prodotto nel listino di una Azienda
    Optional<ProductListing> findByProductIdAndSellerId(Integer productId, Integer sellerId);

    //Trova il prodotto attivo nel listino
    Optional<ProductListing> findByProductIdAndIsActiveTrue(Integer productId);

    ProductListing findProductById(Integer productId );
}