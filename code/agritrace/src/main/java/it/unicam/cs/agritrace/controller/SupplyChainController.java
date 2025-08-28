package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddRowMaterialPayload;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductionStep;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/supplychain")
public class SupplyChainController {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductService productService;
    private ProductMapper productMapper;

    public SupplyChainController(ProductRepository productRepository, CompanyRepository companyRepository,
                                 ProductService productService) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.productService = productService;
    }

    @GetMapping("/getproducts")
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam int companyId) {
        List<ProductDTO> productDTOS = productService.getAllCompanyProductById(companyId);
        return ResponseEntity.ok(productDTOS);
    }
    @PostMapping("/products/{productId}/ingredients")
    public ResponseEntity<?> addRowMaterial (int id, List<AddRowMaterialPayload> payload){
        Product product = productRepository.findById(id).orElseThrow();

        for(AddRowMaterialPayload p : payload){
            Product rawMaterial = productRepository
                    .findById(p.productId())
                    .orElseThrow();
            ProductionStep productionStep = new ProductionStep();
            productionStep.setInputProduct(product);
            productionStep.setOutputProduct(rawMaterial);
            productionStep.setDescription(p.description());
            //TODO
        }
        return ResponseEntity.ok().build();

    }
}
