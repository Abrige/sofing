package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.ReviewRequestDTO;
import it.unicam.cs.agritrace.dtos.responses.RequestResponse;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('CURATORE')")
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
    public ResponseEntity<List<RequestResponse>> requests(@RequestParam(name = "status", required = false) String statusParam) {
        List<RequestResponse> response;
        if (statusParam != null) {
            // case-insensitive
            StatusType statusType = StatusType.fromNameIgnoreCase(statusParam);
            response = requestService.getRequestsByStatus(statusType);
        } else {
            response = requestService.getAllRequests();
        }
        return ResponseEntity.ok(response);
    }

    //Ritorna le richieste che hanno statusName : pending
    @GetMapping("/pending")
    public ResponseEntity<List<RequestResponse>> getPendingApproval() {
        List<RequestResponse> pendingRequests = requestService.getAllPendingRequests();
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
