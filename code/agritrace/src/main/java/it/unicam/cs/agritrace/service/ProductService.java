package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddRawMaterialPayload;
import it.unicam.cs.agritrace.exceptions.ProductNotFoundException;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductionStep;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.ProductionStepRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CompanyRepository companyRepository;
    private final ProductionStepRepository productionStepRepository;


    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          CompanyRepository companyRepository, ProductionStepRepository productionStepRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.companyRepository = companyRepository;
        this.productionStepRepository = productionStepRepository;
    }

    // Ritorna il prodotto (NON CANCELLATO) in base all'id
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id=" + id));
        return productMapper.toDto(product);
    }

    // Ritorna il prodotto no DTO (NON CANCELLATO) in base all'id
    public Product getProductObjById(int id) {
        return productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id=" + id));
    }

    // Prende tutti i prodotti (NON CANCELLATI) e li restituisce come lista di DTO
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findByIsDeletedIsFalse();
        return productMapper.toDtoList(products); // usa il mapper
    }

    // Fa soft-delete del prodotto
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    // Ritorna una lista di prodotti in base all'azienda
    public List<ProductDTO> getAllCompanyProductById(int companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow();
        List<Product> products = productRepository.findByProducerAndIsDeletedFalse(company);
        return productMapper.toDtoList(products); // usa il mapper
    }
}

// Nota: ResourceNotFoundException e UnauthorizedOperationException sono eccezioni custom che dovresti creare.
// Esempio: public class ResourceNotFoundException extends RuntimeException { ... }

