package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.requests.RequestAddProduct;
import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.mappers.RequestMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestService {

    private RequestRepository requestRepository;
    private ProductRepository productRepository;
    private StatusRepository statusRepository;
    private UserRepository userRepository;
    private DbTableRepository dbTableRepository;
    private ObjectMapper objectMapper;
    private final RequestMapper mapper;

    public RequestService(RequestMapper mapper,
                          ObjectMapper objectMapper,
                          DbTableRepository dbTableRepository,
                          UserRepository userRepository,
                          StatusRepository statusRepository,
                          ProductRepository productRepository,
                          RequestRepository requestRepository) {
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.dbTableRepository = dbTableRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.productRepository = productRepository;
        this.requestRepository = requestRepository;
    }

    public Request createProductRequest(User requester, RequestAddProduct dto) {
        try {
            // Serializzo DTO in JSON con formato "new"
            Map<String, Object> payloadMap = new HashMap<>();
            payloadMap.put("name", dto.name());
            payloadMap.put("description", dto.description());
            payloadMap.put("category_id", dto.categoryId());
            payloadMap.put("cultivation_method_id", dto.cultivationMethodId());
            payloadMap.put("harvest_season_id", dto.harvestSeasonId());
            payloadMap.put("producer_id", dto.producerId());

            String payloadJson = objectMapper.writeValueAsString(payloadMap);

            Request request = new Request();
            request.setRequester(requester);
            request.setTargetTable(dbTableRepository.findById(20).orElseThrow());
            request.setTargetId(null);
            request.setRequestType("c");
            request.setPayload(payloadJson);
            request.setCreatedAt(Instant.now());
            request.setStatus(statusRepository.findById(StatusType.fromName("pending")).orElseThrow());

            return requestRepository.save(request);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore serializzazione JSON", e);
        }
    }

    public List<ResponseRequest> getAllDtos() {
        return mapper.toDtoList(requestRepository.findAll());
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
}