package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/trasformatore")
public class TrasformatoreController {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public TrasformatoreController(ProductRepository productRepository, CompanyRepository companyRepository) {
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
}
