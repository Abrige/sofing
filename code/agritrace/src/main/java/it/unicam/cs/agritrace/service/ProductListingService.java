package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.ProductListingDTO;
import it.unicam.cs.agritrace.dtos.requests.AddProductToListing;
import it.unicam.cs.agritrace.dtos.responses.ProductListingResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductListing;
import it.unicam.cs.agritrace.repository.ProductListingRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductListingService {
    private final ProductService productService;
    private final ProductListingRepository productListingRepository;

    public ProductListingService(ProductService productService, ProductListingRepository productListingRepository) {
        this.productService = productService;
        this.productListingRepository = productListingRepository;

    }

    @Transactional
    public void addProductToListing(AddProductToListing productToList, Company company){
        // 1. Trova il prodotto o lancia un eccezione se non esiste
        Product product = productService.findProductById(productToList.product_id());

        //2. Verifica se il prodotto è già in listino
        Optional<ProductListing> existingProductListing = productListingRepository.findByProductId(productToList.product_id());
        // Se presente aggiorna la quantità
        if(existingProductListing.isPresent()){
            ProductListing productListing = existingProductListing.get();
            productListing.setPrice(BigDecimal.valueOf(productListing.getPrice().intValue()
                    + productToList.price().intValue()));
            productListingRepository.save(productListing);
        }else
        {
            //Se non esiste crea il ProductListing
            ProductListing newProductListing = new ProductListing();
            newProductListing.setProduct(product);
            newProductListing.setSeller(company);
            newProductListing.setPrice(productToList.price());
            newProductListing.setQuantityAvailable(productToList.quantityAvailable());
            newProductListing.setUnitOfMeasure(productToList.unitOfMeasure());
            productListingRepository.save(newProductListing);
        }
    }

    @Transactional
    public void removeProductToListing(int productId, Company company) {
        // Trova la ProductListing
        ProductListing productListing = productListingRepository.findByProductIdAndSellerId(productId, company.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Prodotto non trovato nel listino dell'azienda"));

        // Controlla che non sia già stato disattivato
        if (!productListing.getIsActive()) {
            throw new IllegalStateException("Questo prodotto è già stato rimosso dal listino.");
        }
        productListing.setIsActive(false);
        productListingRepository.save(productListing);
    }

    public List<ProductListingResponse> getAllListing() {
        return productListingRepository.findAll().stream().map(
                productListing -> new ProductListingResponse(
                        productListing.getId(),
                        productListing.getProduct().getName(),
                        productListing.getSeller().getName(),
                        productListing.getPrice(),
                        productListing.getQuantityAvailable(),
                        productListing.getUnitOfMeasure()
                ))
                .toList();
    }

    public ProductListingResponse getListingbyId(int id){
        ProductListing productListing = productListingRepository.findProductById(id);

        return new ProductListingResponse(
                productListing.getId(),
                productListing.getProduct().getName(),
                productListing.getSeller().getName(),
                productListing.getPrice(),
                productListing.getQuantityAvailable(),
                productListing.getUnitOfMeasure()
        );


    }

    public ProductListing getProductListingById(int id) {
        return productListingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Prodotto di listino con id= " + id + " non trovato"));
    }
}
