package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddRawMaterialPayload;
import it.unicam.cs.agritrace.exceptions.ProductNotFoundException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
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
    private final ProductCategoryRepository productCategoryRepository;
    private final CultivationMethodRepository cultivationMethodRepository;
    private final HarvestSeasonRepository harvestSeasonRepository;


    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          CompanyRepository companyRepository, ProductionStepRepository productionStepRepository,
                          ProductCategoryRepository productCategoryRepository,
                          CultivationMethodRepository cultivationMethodRepository,
                          HarvestSeasonRepository harvestSeasonRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.companyRepository = companyRepository;
        this.productionStepRepository = productionStepRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.cultivationMethodRepository = cultivationMethodRepository;
        this.harvestSeasonRepository = harvestSeasonRepository;
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

    public Product findProductById(int id) {
        return productRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id=" + id));
    }

    public ProductCategory getProductCategoryById(int id) {
        return productCategoryRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product category not found with id=" + id));
    }

    public CultivationMethod getCultivationMethodById(int id){
        return cultivationMethodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cultivation method not found with id=" + id));
    }

    public HarvestSeason getHarvestSeasonById(int id) {
        return harvestSeasonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Harvest season not found with id=" + id));
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}

// Nota: ResourceNotFoundException e UnauthorizedOperationException sono eccezioni custom che dovresti creare.
// Esempio: public class ResourceNotFoundException extends RuntimeException { ... }

