package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.common.ReviewRequestDTO;
import it.unicam.cs.agritrace.dtos.responses.RequestResponse;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.RequestMapper;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.Status;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.*;
import it.unicam.cs.agritrace.service.factory.RequestHandlerFactory;
import it.unicam.cs.agritrace.service.handler.RequestHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class RequestService {

    private final RequestTypeRepository requestTypeRepository;
    private final RequestRepository requestRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final DbTableRepository dbTableRepository;
    private final ObjectMapper objectMapper;
    private final RequestMapper requestMapper;
    private final RequestHandlerFactory requestHandlerFactory;

    public RequestService(RequestMapper requestMapper,
                          ObjectMapper objectMapper,
                          DbTableRepository dbTableRepository,
                          UserRepository userRepository,
                          StatusRepository statusRepository,
                          RequestRepository requestRepository,
                          RequestTypeRepository requestTypeRepository,
                          RequestHandlerFactory requestHandlerFactory) {
        this.requestMapper = requestMapper;
        this.objectMapper = objectMapper;
        this.dbTableRepository = dbTableRepository;
        this.userRepository = userRepository;
        this.statusRepository = statusRepository;
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.requestHandlerFactory = requestHandlerFactory;
    }

    // Prende tutte le richieste del curatore
    @Transactional(readOnly = true)
    public List<RequestResponse> getAllRequests() {
        List<Request> entities = requestRepository.findAll();
        if (entities.isEmpty()) {
            throw new ResourceNotFoundException("No requests found");
        }
        return requestMapper.toDtoList(entities);
    }

    @Transactional(readOnly = true)
    public List<RequestResponse> getAllPendingRequests() {
        List<Request> entities = requestRepository.findByStatusIsPending();
        if (entities.isEmpty()) {
            throw new ResourceNotFoundException("No requests found");
        }
        return requestMapper.toDtoList(entities);
    }

    public List<RequestResponse> getRequestsByStatus(StatusType status) {
        return requestRepository.findByStatus_Id(status.getId())
                .stream()
                .map(requestMapper::toDto)
                .toList();
    }

    @Transactional
    public void reviewRequest(ReviewRequestDTO reviewRequestDTO, User curator) {
        Request request = requestRepository.findById(reviewRequestDTO.requestId())
                .orElseThrow(() -> new RuntimeException("Request non trovata con id=" + reviewRequestDTO.requestId()));

        // Se la action è reject → chiudo subito la richiesta
        if (StatusType.REJECTED.equals(reviewRequestDTO.action())) {
            Status rejectedStatus = statusRepository.findById(StatusType.REJECTED.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Status REJECTED non trovato nel db"));
            request.setStatus(rejectedStatus);
            request.setReviewedAt(Instant.now());
            request.setCurator(curator);
            request.setDecisionNotes(reviewRequestDTO.decisionNotes());
            requestRepository.save(request);
            return;
        }

        // Se non è reject, allora deve essere approve
        if (!StatusType.ACCEPTED.equals(reviewRequestDTO.action())) {
            throw new IllegalArgumentException("Azione non valida: " + reviewRequestDTO.action());
        }

        // Recupero handler dalla factory in base al tipo di request
        RequestHandler handler = requestHandlerFactory.getHandler(request.getRequestType());

        if (handler == null) {
            throw new RuntimeException("Nessun handler trovato per request type: " + request.getRequestType().getName());
        }

        // Eseguo la logica di business specifica del tipo
        handler.handle(request);

        // Aggiorno lo status della request ad ACCEPTED
        Status acceptedStatus = statusRepository.findById(StatusType.ACCEPTED.getId())
                .orElseThrow(() -> new RuntimeException("Status ACCEPTED non trovato"));
        request.setStatus(acceptedStatus);
        request.setCurator(curator);
        request.setReviewedAt(Instant.now());
        requestRepository.save(request);
    }
}