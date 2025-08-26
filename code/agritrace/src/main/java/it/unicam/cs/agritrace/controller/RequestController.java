package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public ResponseEntity<List<ResponseRequest>> requests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }
}
