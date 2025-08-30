package it.unicam.cs.agritrace.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.unicam.cs.agritrace.dtos.common.ReviewRequestDTO;
import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final UserRepository userRepository;

    public RequestController(RequestService requestService, UserRepository userRepository) {
        this.requestService = requestService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<ResponseRequest>> requests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    //Ritorna le richieste che hanno statusName : pending
    @GetMapping("/pending")
    public ResponseEntity<List<ResponseRequest>> getPendingApproval() {
        List<ResponseRequest> pendingRequests = requestService.getAllPendingRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    //Approvazione richiesta
    @PostMapping("/review")
    public ResponseEntity<Void> reviewRequest(@RequestBody ReviewRequestDTO reviewRequestDTO)  {
        User curator = userRepository.findById(1).orElseThrow();
        requestService.reviewRequest(reviewRequestDTO, curator);
        return ResponseEntity.ok().build();
    }

}
