package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private RequestRepository requestRepository;

    @GetMapping("/requests")
    public ResponseEntity<?> requests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }
}
