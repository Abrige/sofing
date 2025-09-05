package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.common.ReviewRequestDTO;
import it.unicam.cs.agritrace.dtos.responses.RequestResponse;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.RequestMapper;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
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

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final RequestHandlerFactory requestHandlerFactory;
    private final StatusService statusService;
    private final RequestTypeRepository requestTypeRepository;

    public RequestService(RequestMapper requestMapper,
                          RequestRepository requestRepository,
                          RequestHandlerFactory requestHandlerFactory,
                          StatusService statusService,
                          RequestTypeRepository requestTypeRepository) {
        this.requestMapper = requestMapper;

        this.requestRepository = requestRepository;
        this.requestHandlerFactory = requestHandlerFactory;
        this.statusService = statusService;
        this.requestTypeRepository = requestTypeRepository;
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
        Request request = getRequestById(reviewRequestDTO.requestId());
        Status pendingStatus = statusService.getStatusByName("pending");

        // Se lo status non è pending non va bene, non puoi usare quella richiesta, lancia un errore
        if(!request.getStatus().equals(pendingStatus)) {
            throw new IllegalArgumentException("Selezionare una Request non in pending non si può fare");
        }

        // Se la action è reject → chiudo subito la richiesta
        if (StatusType.REJECTED.equals(reviewRequestDTO.action())) {
            Status rejectedStatus = statusService.getStatusByName("pending");
            request.setStatus(rejectedStatus);
            request.setReviewedAt(Instant.now());
            request.setCurator(curator);
            if(reviewRequestDTO.decisionNotes() != null) {
                request.setDecisionNotes(reviewRequestDTO.decisionNotes());
            }
            requestRepository.save(request);
            return;
        }

        // Se non è reject, allora deve essere approve
        if (!StatusType.ACCEPTED.equals(reviewRequestDTO.action())) {
            throw new IllegalArgumentException("Azione non valida: " + reviewRequestDTO.action());
        }

        // Recupero handler dalla factory in base al tipo di request
        RequestHandler handler = requestHandlerFactory.getHandler(request.getRequestType());

        // Eseguo la logica di business specifica del tipo
        handler.handle(request);

        // Aggiorno lo status della request ad ACCEPTED
        Status acceptedStatus = statusService.getStatusById(StatusType.ACCEPTED.getId());
        request.setStatus(acceptedStatus);
        request.setCurator(curator);
        request.setReviewedAt(Instant.now());
        requestRepository.save(request);
    }

    public Request getRequestById(int id) {
        return requestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request non trovata con id=" + id));
    }

    public RequestType getRequestTypeById(int id) {
        return requestTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request non trovata con id=" + id));
    }

    public RequestType getRequestTypeByName(String name) {
        return requestTypeRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Request non trovata con nome=" + name));
    }
}