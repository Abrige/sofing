package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddRawMaterialPayload;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductionStep;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.service.ProductService;
import it.unicam.cs.agritrace.service.SupplyChainService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/supplychain")
public class SupplyChainController {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductService productService;
    private final SupplyChainService supplyChainService;
    private ProductMapper productMapper;

    public SupplyChainController(ProductRepository productRepository, CompanyRepository companyRepository,
                                 ProductService productService, SupplyChainService supplyChainService) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.productService = productService;
        this.supplyChainService = supplyChainService;
    }

    @GetMapping("/getproducts")
    public ResponseEntity<List<ProductDTO>> getProducts(@RequestParam int companyId) {
        List<ProductDTO> productDTOS = productService.getAllCompanyProductById(companyId);
        return ResponseEntity.ok(productDTOS);
    }

    @PostMapping("/products/{productId}/ingredients")
    public ResponseEntity<Void> addRawMaterial (@PathVariable int productId,
                                             @RequestBody List<AddRawMaterialPayload> payloads){
        supplyChainService.addRawMaterials(productId, payloads);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{productId}/ingredients/{ingredientId}")
    public ResponseEntity<Void> removeRawMaterial (@PathVariable int productId, @PathVariable int ingredientId){
        supplyChainService.removeRawMaterial(productId, ingredientId);
        return ResponseEntity.ok().build();
    }
    @GetMapping ("/products/{productId}/ingredients")
    public ResponseEntity<List<ProductDTO>> getRawMaterialsByOutputProduct (@PathVariable int productId){
        return ResponseEntity.ok(supplyChainService.getRawMaterialsByOutputProduct(productId));

    }
}
