package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.responses.ProductCategoryResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.ProductCategory;
import it.unicam.cs.agritrace.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;


    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }


    public List<ProductCategoryResponse> getProductCategoryAll() {
        List<ProductCategory> productCategory = productCategoryRepository.findAll();

        List<ProductCategoryResponse> productCategoryResponse = productCategory.stream().map(
                pC -> new ProductCategoryResponse(
                        pC.getId(),
                        pC.getName(),
                        pC.getDescription()
                )
        ).toList();

        return productCategoryResponse;
    }

    public ProductCategoryResponse getProductCategorybyId(@PathVariable Integer id){
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria non trovata" + id));

        return new ProductCategoryResponse(
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getDescription()
        );

    }
}


