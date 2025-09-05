
package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.payloads.CertificationPayload;
import it.unicam.cs.agritrace.dtos.requests.CertificationRequest;
import it.unicam.cs.agritrace.dtos.responses.CertificationsResponse;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.service.CertificationsService;
import it.unicam.cs.agritrace.validators.create.ValidPackageCreate;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CertificationsResponse> getCertificationById(@PathVariable int id){
        return ResponseEntity.ok(certificationsService.GetCertificationsById(id));
    }
    @PostMapping("/list")
    public ResponseEntity<OperationResponse> createCertification(@Valid @ValidPackageCreate @RequestBody CertificationPayload certification){
        OperationResponse operationResponse = certificationsService.createCertification(certification);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationResponse);
    }
    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> deleteCertificationById(@PathVariable int id){
        certificationsService.deleteCertification(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{certificationId}/product/{productId}")
    public ResponseEntity<?> addCertificationToProduct(@PathVariable int certificationId, @PathVariable int productId){
        certificationsService.addCertificationToProduct(certificationId, productId);
        return ResponseEntity.ok("Certificazione aggiunta al prodotto con successo");
    }
}