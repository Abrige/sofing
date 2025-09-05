
package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.payloads.CertificationPayload;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.responses.CertificationsResponse;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.service.CertificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CertificationsResponse>> getAllCertifications(){
        return ResponseEntity.ok(certificationService.getCertifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationsResponse> getCertificationById(@PathVariable int id){
        return ResponseEntity.ok(certificationService.getCertificationsById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<OperationResponse> createCertification(@Valid @RequestBody CertificationPayload certification){
        OperationResponse operationResponse = certificationService.createCertification(certification);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<OperationResponse> deleteCertificationById(@PathVariable int id){
        OperationResponse response =  certificationService.deleteCertificationRequest(new DeletePayload(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{certificationId}/product/{productId}")
    public ResponseEntity<String> addCertificationToProduct(@PathVariable @Min(1) int certificationId, @PathVariable @Min(1) int productId){
        certificationService.addCertificationToProduct(certificationId, productId);
        return ResponseEntity.ok("Certificazione aggiunta al prodotto con successo");
    }
}