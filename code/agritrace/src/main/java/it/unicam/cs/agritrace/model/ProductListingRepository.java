package it.unicam.cs.agritrace.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductListingRepository extends JpaRepository<ProductListing, Integer> {
}