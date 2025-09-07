package it.unicam.cs.agritrace.controller;


import it.unicam.cs.agritrace.dtos.common.ProductCategoryDTO;
import it.unicam.cs.agritrace.dtos.responses.ProductCategoryResponse;
import it.unicam.cs.agritrace.service.ProductCategoryService;
import it.unicam.cs.agritrace.service.ProductListingService;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/product-category")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    private final RequestService requestService;


    public ProductCategoryController(ProductCategoryService productCategoryService, RequestService requestService) {
        this.productCategoryService = productCategoryService;
        this.requestService = requestService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductCategoryResponse>> getAllProductCategory(){
        List<ProductCategoryResponse> productCategory = productCategoryService.getProductCategoryAll();

        return ResponseEntity.ok(productCategory);

    }
}
