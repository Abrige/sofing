package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.payloads.ProductPayload;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CompanyRepository companyRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CultivationMethodRepository cultivationMethodRepository;
    private final HarvestSeasonRepository harvestSeasonRepository;
    private final RequestRepository requestRepository;
    private final RequestFactory requestFactory;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          CompanyRepository companyRepository, ProductionStepRepository productionStepRepository,
                          ProductCategoryRepository productCategoryRepository,
                          CultivationMethodRepository cultivationMethodRepository,
                          HarvestSeasonRepository harvestSeasonRepository,
                          RequestRepository requestRepository,
                          RequestFactory requestFactory) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.companyRepository = companyRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.cultivationMethodRepository = cultivationMethodRepository;
        this.harvestSeasonRepository = harvestSeasonRepository;
        this.requestRepository = requestRepository;
        this.requestFactory = requestFactory;
    }

    // Ritorna il prodotto (NON CANCELLATO) in base all'id
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id=" + id));
        return productMapper.toDto(product);
    }

    // Ritorna il prodotto no DTO (NON CANCELLATO) in base all'id
    public Product getProductObjById(int id) {
        return productRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id=" + id));
    }

    // Prende tutti i prodotti (NON CANCELLATI) e li restituisce come lista di DTO
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findByIsDeletedIsFalse();
        return productMapper.toDtoList(products); // usa il mapper
    }

    public OperationResponse deleteProductRequest(DeletePayload payload) {
        if(!existsProductById(payload.targetId())){
            throw new ResourceNotFoundException("Product not found with id=" + payload.targetId());
        }

        Request request = requestFactory.createRequest(
                "PRODUCTS",
                "DELETE_PRODUCT",
                payload
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PRODUCT", "DELETE", savedRequest.getCreatedAt());
    }

    public OperationResponse updateProductRequest(ProductPayload payload) {
        // Se il prodotto con quell'id non esiste lancia una eccezione
        if(!existsProductById(payload.productId())){
            throw new ResourceNotFoundException("Product not found with id=" + payload.productId());
        }

        Request request = requestFactory.createRequest(
                "PRODUCTS",
                "UPDATE_PRODUCT",
                payload
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PRODUCT", "UPDATE", savedRequest.getCreatedAt());
    }

    public OperationResponse createProductRequest(ProductPayload payload) {
        Request request = requestFactory.createRequest(
                "PRODUCTS",
                "ADD_PRODUCT",
                payload
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PRODUCT", "CREATE", savedRequest.getCreatedAt());
    }

    // Ritorna una lista di prodotti in base all'azienda
    public List<ProductDTO> getAllCompanyProductById(int companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow();
        List<Product> products = productRepository.findByProducerAndIsDeletedFalse(company);
        return productMapper.toDtoList(products); // usa il mapper
    }

    public Product findProductById(int id) {
        return productRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id=" + id));
    }

    public ProductCategory getProductCategoryById(int id) {
        return productCategoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product category not found with id=" + id));
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

    public boolean existsProductById(int id) {
        return productRepository.existsByIdAndIsDeletedFalse(id);
    }
}

