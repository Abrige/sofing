package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.ProductListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductListingRepository extends JpaRepository<ProductListing, Integer> {
}