package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.ProductCategoryDTO;
import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.responses.ProductCategoryResponse;
import it.unicam.cs.agritrace.dtos.responses.ProductListingResponse;
import it.unicam.cs.agritrace.model.ProductCategory;
import it.unicam.cs.agritrace.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}


