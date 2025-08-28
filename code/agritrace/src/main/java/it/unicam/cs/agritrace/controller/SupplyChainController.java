package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddRowMaterialPayload;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductionStep;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/supplychain")
public class SupplyChainController {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public SupplyChainController(ProductRepository productRepository, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
    }

    @RequestMapping("/getproducts")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        //TODO Utente fittizio
        Company company = companyRepository.findById(1).orElseThrow();
        List<ProductDTO> products = productRepository
                .findAll()
                .stream()
                .filter(item -> item.getProducer().equals(company))
                .map(ProductDTO::new).toList();
        return ResponseEntity.ok(products);
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
