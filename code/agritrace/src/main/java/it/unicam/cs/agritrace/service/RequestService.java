package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.requests.ProductCreationRequest;
import it.unicam.cs.agritrace.dtos.responses.ProductCreationResponse;
import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.exceptions.PayloadParsingException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.RequestMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class RequestService {

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
                          RequestRepository requestRepository) {
        this.requestMapper = requestMapper;
        this.objectMapper = objectMapper;
        this.dbTableRepository = dbTableRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.productRepository = productRepository;
        this.requestRepository = requestRepository;
    }

    public ProductCreationResponse createProductRequest(ProductCreationRequest dto) {
        // TODO controllare se il prodotto esiste già e in tal caso non fare la request ma lanciare un errore??
        // 🔹 Recupero l'utente fittizio con id=1
        User requester = userRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Fake user with id=1 not found. Insert it in DB first."));

        // 🔹 Serializzo payload
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

        // 🔹 Recupero tabella target e status
        var targetTable = dbTableRepository.findById(20)
                .orElseThrow(() -> new ResourceNotFoundException("Target table with id=20 not found"));
        var status = statusRepository.findById(StatusType.fromName("pending"))
                .orElseThrow(() -> new ResourceNotFoundException("Status 'pending' not found"));

        // 🔹 Creo la request
        Request request = new Request();
        request.setRequester(requester);
        request.setTargetTable(targetTable);
        request.setTargetId(null);
        request.setRequestType("c");
        request.setPayload(payloadJson);
        request.setCreatedAt(Instant.now());
        request.setStatus(status);

        // 🔹 Salvo
        Request created = requestRepository.save(request);

        // 🔹 Ritorno direttamente il DTO
        return requestMapper.toProductCreationResponse(created);
    }


    @Transactional(readOnly = true)
    public List<ResponseRequest> getAllRequests() {
        List<Request> entities = requestRepository.findAll();
        if (entities.isEmpty()) {
            throw new ResourceNotFoundException("No requests found");
        }
        return requestMapper.toDtoList(entities);
    }
}


    /*
    public Product approveRequest(Integer requestId, User curator) throws IOException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));

        request.setCurator(curator);
        request.setReviewedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC));

        // Recupero stato "SHIPPED" (o "APPROVED" se lo rinomini) dal DB usando enum
        Status approvedStatus = statusRepository.findById(StatusType.SHIPPED.getId())
                .orElseThrow(() -> new IllegalStateException("Missing status: " + StatusType.SHIPPED));

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