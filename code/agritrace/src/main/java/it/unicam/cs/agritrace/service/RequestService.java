package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final ProductRepository productRepository;
    private final StatusRepository statusRepository;

    public RequestService(RequestRepository requestRepository,
                          ProductRepository productRepository,
                          StatusRepository statusRepository) {
        this.requestRepository = requestRepository;
        this.productRepository = productRepository;
        this.statusRepository = statusRepository;
    }

    public Request createProductRequest(User requester, DbTable targetTable, String payload) {
        Request request = new Request();
        request.setRequester(requester);
        request.setTargetTable(targetTable);
        request.setRequestType("c"); // create
        request.setPayload(payload);

        // niente setStatus() → il DB mette automaticamente PENDING

        return requestRepository.save(request);
    }

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
}