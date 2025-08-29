package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.common.PackageItemDTO;
import it.unicam.cs.agritrace.dtos.requests.ProductCreationRequest;
import it.unicam.cs.agritrace.dtos.responses.ProductCreationResponse;
import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.exceptions.PayloadParsingException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PackageMapper;
import it.unicam.cs.agritrace.mappers.RequestMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private final ProductCategoryRepository productCategoryRepository;
    private final CultivationMethodRepository cultivationMethodRepository;
    private final HarvestSeasonRepository harvestSeasonRepository;
    private final CompanyRepository companyRepository;
    private final RequestTypeRepository requestTypeRepository;
    private RequestRepository requestRepository;
    private ProductRepository productRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final DbTableRepository dbTableRepository;
    private final ObjectMapper objectMapper;
    private final RequestMapper requestMapper;

    public RequestService(RequestMapper requestMapper,
                          ObjectMapper objectMapper,
                          DbTableRepository dbTableRepository,
                          UserRepository userRepository,
                          StatusRepository statusRepository,
                          ProductRepository productRepository,
                          RequestRepository requestRepository,
                          ProductCategoryRepository productCategoryRepository,
                          CultivationMethodRepository cultivationMethodRepository,
                          HarvestSeasonRepository harvestSeasonRepository,
                          CompanyRepository companyRepository,
                          RequestTypeRepository requestTypeRepository) {
        this.requestMapper = requestMapper;
        this.objectMapper = objectMapper;
        this.dbTableRepository = dbTableRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.productRepository = productRepository;
        this.requestRepository = requestRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.cultivationMethodRepository = cultivationMethodRepository;
        this.harvestSeasonRepository = harvestSeasonRepository;
        this.companyRepository = companyRepository;
        this.requestTypeRepository = requestTypeRepository;
    }

    public ProductCreationResponse createProductRequest(ProductCreationRequest dto) {
        // TODO controllare se il prodotto esiste già e in tal caso non fare la request ma lanciare un errore??
        // Recupero l'utente fittizio con id=1
        User requester = userRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Fake user with id=1 not found. Insert it in DB first."));

        // Serializzo payload
        String payloadJson;
        try {
            Map<String, Object> payloadMap = Map.of(
                    "name", dto.name(),
                    "description", dto.description(),
                    "category_id", dto.categoryId(),
                    "cultivation_method_id", dto.cultivationMethodId(),
                    "harvest_season_id", dto.harvestSeasonId(),
                    "producer_id", dto.producerId()
            );
            payloadJson = objectMapper.writeValueAsString(payloadMap);
        } catch (JsonProcessingException e) {
            throw new PayloadParsingException("Errore serializzazione JSON per product request", e);
        }

        // Recupero tabella target e status
        var targetTable = dbTableRepository.findById(20)
                .orElseThrow(() -> new ResourceNotFoundException("Target table with id=20 not found"));
        var status = statusRepository.findById(StatusType.fromName("pending"))
                .orElseThrow(() -> new ResourceNotFoundException("Status 'pending' not found"));

        // Creo la request
        Request request = new Request();
        request.setRequester(requester);
        request.setTargetTable(targetTable);
        request.setTargetId(null);
        RequestType requestType = requestTypeRepository.findById(1).orElseThrow();
        request.setRequestType(requestType);
        request.setPayload(payloadJson);
        request.setCreatedAt(Instant.now());
        request.setStatus(status);

        // Salvo
        Request created = requestRepository.save(request);

        // Ritorno direttamente il DTO
        return requestMapper.toProductCreationResponse(created);
    }

    // Prende tutte le richieste del curatore
    @Transactional(readOnly = true)
    public List<ResponseRequest> getAllRequests() {
        List<Request> entities = requestRepository.findAll();
        if (entities.isEmpty()) {
            throw new ResourceNotFoundException("No requests found");
        }
        return requestMapper.toDtoList(entities);
    }

    @Transactional(readOnly = true)
    public List<ResponseRequest> getAllPendingRequests() {
        List<Request> entities = requestRepository.findByStatusIsPending();
        if (entities.isEmpty()) {
            throw new ResourceNotFoundException("No requests found");
        }
        return requestMapper.toDtoList(entities);
    }

    // Accetta una richiesta da parte del curatore
    public void approveRequest(int id,
                               User curator) throws JsonProcessingException {
        // Prende la richiesta in base all'id
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request with id=" + id + " not found"));

        // Recupero stato "SHIPPED" (o "APPROVED" se lo rinomini) dal DB usando enum
        Status approvedStatus = statusRepository.findById(StatusType.ACCEPTED.getId())
                .orElseThrow(() -> new IllegalStateException("Missing status: " + StatusType.ACCEPTED));

        request.setCurator(curator);
        request.setStatus(approvedStatus);
        request.setReviewedAt(Instant.now());
        requestRepository.save(request);

        //TODO
        if ("c".equals(request.getRequestType())) {
            // ad esempio: creare un record in Product
            // Creazione reale del prodotto dal payload JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode data = mapper.readTree(request.getPayload());

            Product product = new Product();
            product.setName(data.get("name").asText());
            product.setDescription(data.get("description").asText());
            product.setCategory(productCategoryRepository.findById(data.get("category_id").asInt())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria non trovata")));
            product.setCultivationMethod(cultivationMethodRepository.findById(data.get("cultivation_method_id").asInt())
                    .orElseThrow(() -> new IllegalArgumentException("Metodo di coltivazione non trovato")));
            product.setHarvestSeason(harvestSeasonRepository.findById(data.get("harvest_season_id").asInt())
                    .orElseThrow(() -> new IllegalArgumentException("Stagione di Raccolta non trovata")));
            // product.setIsDeleted(false); DEFAULT VALUE
            product.setProducer(companyRepository.findById(data.get("producer_id").asInt())
                    .orElseThrow(() -> new IllegalArgumentException("Produttore non trovato")));
            Product savedProduct = productRepository.save(product);
            request.setTargetId(savedProduct.getId());
            requestRepository.save(request);
        }
        //TODO notifica al curatore
    }

    public void rejectRequest(int id,
                              User curator,
                              String reason) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request with id=" + id + " not found"));
        Status status = statusRepository.findById(StatusType.SHIPPED.getId())
                .orElseThrow(() -> new IllegalStateException("Missing status: " + StatusType.SHIPPED));
        request.setStatus(status);
        request.setCurator(curator);
        request.setReviewedAt(Instant.now());
        request.setDecisionNotes(reason);
        requestRepository.save(request);
        //TODO notifica al curatore
    }
}
/*
    public void acceptPackageRequest(Request request){
        Map<Integer, Product> productMap = productRepository.findAllById(
                addPackagePayload.items().stream().map(PackageItemDTO::productId).toList()
        ).stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        TypicalPackage pkg = new TypicalPackage();
        pkg.setName(addPackagePayload.name());
        pkg.setDescription(addPackagePayload.description());
        pkg.setPrice(addPackagePayload.price());

        Company producer = companyRepository.findById(addPackagePayload.producerId())
                .orElseThrow(() -> new IllegalArgumentException("Producer non trovato con id: " + addPackagePayload.producerId()));
        pkg.setProducer(producer);

        Set<TypicalPackageItem> packageItems = addPackagePayload.items().stream()
                .map(dto -> {
                    Product product = productMap.get(dto.productId());
                    if (product == null) throw new IllegalArgumentException("Prodotto non trovato: " + dto.productId());

                    TypicalPackageItem item = new TypicalPackageItem();
                    item.setProduct(product);
                    item.setQuantity(dto.quantity());
                    item.setTypicalPackage(pkg);
                    return item;
                })
                .collect(Collectors.toSet());

        pkg.setTypicalPackageItems(packageItems);

        typicalPackageRepository.save(pkg);

        // Ritorna DTO
        return PackageMapper.toDTO(pkg);
    }

 */



    /*
    public Product approveRequest(Integer requestId, User curator) throws IOException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));

        // Recupero stato "SHIPPED" (o "APPROVED" se lo rinomini) dal DB usando enum
        Status approvedStatus = statusRepository.findById(StatusType.SHIPPED.getId())
                .orElseThrow(() -> new IllegalStateException("Missing status: " + StatusType.SHIPPED));

        request.setCurator(curator);
        request.setReviewedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        request.setStatus(approvedStatus);

        // Creazione reale del prodotto dal payload JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode data = mapper.readTree(request.getPayload());

        Product product = new Product();
        product.setName(data.get("name").asText());
        product.setDescription(data.get("description").asText());
        product.setCategory(new ProductCategory(data.get("category_id").asLong()));
        product.setCultivationMethod(new CultivationMethod(data.get("cultivation_method_id").asLong()));
        product.setHarvestSeason(new HarvestSeason(data.get("harvest_season_id").asLong()));
        // product.setIsDeleted(false); DEFAULT VALUE

        Product savedProduct = productRepository.save(product);
        request.setTargetId(savedProduct.getId());
        requestRepository.save(request);

        return savedProduct;
    }

    public Request rejectRequest(Long requestId, User curator, String notes) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));

        request.setCurator(curator);
        request.setReviewedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        request.setDecisionNotes(notes);

        // Recupero stato "REJECTED"
        Status rejectedStatus = statusRepository.findById(StatusType.REJECTED.getId())
                .orElseThrow(() -> new IllegalStateException("Missing status: " + StatusType.REJECTED));

        request.setStatus(rejectedStatus);

        return requestRepository.save(request);
    }

     */