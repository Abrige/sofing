package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.payloads.ProductCreateUpdatePayload;
import it.unicam.cs.agritrace.dtos.payloads.ProductPayload;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          CompanyRepository companyRepository,
                          ProductCategoryRepository productCategoryRepository,
                          CultivationMethodRepository cultivationMethodRepository,
                          HarvestSeasonRepository harvestSeasonRepository,
                          RequestRepository requestRepository,
                          RequestFactory requestFactory,
                          UserService userService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.companyRepository = companyRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.cultivationMethodRepository = cultivationMethodRepository;
        this.harvestSeasonRepository = harvestSeasonRepository;
        this.requestRepository = requestRepository;
        this.requestFactory = requestFactory;
        this.userService = userService;
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
        Product product = productRepository.findById(payload.targetId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id=" + payload.targetId()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

        // Prende la company dell'utente
        Company company = companyRepository.findByOwnerIdAndIsDeletedFalse(requester.getId())
                .orElseThrow(() -> new AccessDeniedException("L'utente non ha nessuna azienda associata"));

        // Se il produttore di quel prodotto non è uguale al produttore che si sta cercando di modificare cioè quello
        // per cui si fa la richiesta di modifica
        if (!product.getProducer().getId().equals(company.getId())) {
            throw new AccessDeniedException("Non puoi eliminare prodotti che non appartengono alla tua azienda");
        }

        Request request = requestFactory.createRequest(
                "PRODUCTS",
                "DELETE_PRODUCT",
                payload,
                requester
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PRODUCT", "DELETE", savedRequest.getCreatedAt());
    }

    public OperationResponse updateProductRequest(ProductCreateUpdatePayload payload) {
        // Recupero l'utente loggato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

        // Recupero la company dell'utente
        Company company = companyRepository.findByOwnerIdAndIsDeletedFalse(requester.getId())
                .orElseThrow(() -> new AccessDeniedException("L'utente non ha nessuna azienda associata"));

        // Recupero il prodotto esistente
        Product existing = productRepository.findById(payload.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id=" + payload.productId()));


        // Controllo che il prodotto appartenga alla company dell'utente
        if (!existing.getProducer().getId().equals(company.getId())) {
            throw new AccessDeniedException("Non puoi modificare prodotti che non appartengono alla tua azienda");
        }

        // Aggiorno solo i campi presenti nel payload
        if (payload.categoryId() != null) {
            if(!productCategoryRepository.existsById(payload.categoryId())){
                    throw new ResourceNotFoundException("Product category not found with id=" + payload.categoryId());
            }
        }
        if (payload.cultivationMethodId() != null) {
            if(!cultivationMethodRepository.existsById(payload.cultivationMethodId())){
                    throw  new ResourceNotFoundException("Cultivation category not found with id=" + payload.cultivationMethodId());
            }
        }
        if (payload.harvestSeasonId() != null) {
            if(!harvestSeasonRepository.existsById(payload.harvestSeasonId())) {
                    throw new ResourceNotFoundException("Harvest season not found with id=" + payload.harvestSeasonId());
            }
        }

        // Creo la request per tracciare l'update
        Request request = requestFactory.createRequest(
                "PRODUCTS",
                "UPDATE_PRODUCT",
                payload,
                requester
        );

        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PRODUCT", "UPDATE", savedRequest.getCreatedAt());
    }

    public OperationResponse createProductRequest(ProductCreateUpdatePayload payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

        // Recupero l’azienda dell’utente
        Company company = companyRepository.findByOwnerIdAndIsDeletedFalse(requester.getId())
                .orElseThrow(() -> new AccessDeniedException("L'utente non ha nessuna azienda associata"));

        // Inietto il producerId nel payload prima di salvarlo nella request
        ProductPayload payloadWithProducer = new ProductPayload(
                payload.productId(),
                payload.name(),
                payload.description(),
                payload.categoryId(),
                payload.cultivationMethodId(),
                payload.harvestSeasonId(),
                company.getId() // producerId automatico
        );

        Request request = requestFactory.createRequest(
                "PRODUCTS",
                "ADD_PRODUCT",
                payloadWithProducer,
                requester
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

