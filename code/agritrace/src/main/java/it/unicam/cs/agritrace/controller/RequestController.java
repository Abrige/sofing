package it.unicam.cs.agritrace.controller;

<<<<<<< HEAD
import ch.qos.logback.core.model.Model;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequestController {
    private final RequestService requestService;
    private RequestRepository requestRepository;

    public RequestController(RequestRepository requestRepository,
                             RequestService requestService) {
        this.requestRepository = requestRepository;
        this.requestService = requestService;
    }

    @GetMapping("/requests")
    public ResponseEntity<?> requests() {
        return ResponseEntity.ok(requestService.getAllDtos());
=======
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
>>>>>>> origin/main
    }
}
