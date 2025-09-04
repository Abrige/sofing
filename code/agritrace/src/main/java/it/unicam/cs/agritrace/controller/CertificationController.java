package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.responses.CertificationsResponse;
import it.unicam.cs.agritrace.service.CertificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {

    private final CertificationsService certificationsService;

    public CertificationController(CertificationsService certificationsService) {
        this.certificationsService = certificationsService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CertificationsResponse>> getAllCertifications(){
        return ResponseEntity.ok(certificationsService.GetCertifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationsResponse> getIdCertifications(@PathVariable int id){
        return ResponseEntity.ok(certificationsService.GetCertificationsById(id));
    }
}
