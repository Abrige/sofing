
package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.payloads.CertificationPayload;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.requests.AddProductCertificationRequest;
import it.unicam.cs.agritrace.dtos.responses.CertificationResponse;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.service.CertificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certifications")
@Tag(name = "Certificazioni", description = "Gestione delle certificazioni nel sistema")
public class CertificationController {

    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @GetMapping("/list")
    @Operation(
            summary = "Lista certificazioni",
            description = "Ritorna tutte le certificazioni registrate nel sistema."
    )
    @ApiResponse(responseCode = "200", description = "Certificazioni recuperate con successo")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<List<CertificationResponse>> getAllCertifications(){
        return ResponseEntity.ok(certificationService.getCertifications());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera certificazione per ID",
            description = "Restituisce i dettagli di una certificazione specifica."
    )
    @ApiResponse(responseCode = "200", description = "Certificazione recuperata con successo")
    @ApiResponse(responseCode = "404", description = "Certificazione non trovata")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<CertificationResponse> getCertificationById(@PathVariable int id){
        return ResponseEntity.ok(certificationService.getCertificationsById(id));
    }

    @PostMapping("/create")
    @Operation(
            summary = "Crea una nuova certificazione",
            description = "Crea una richiesta di certificazione nel sistema.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati della certificazione da creare",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Certificazione esempio",
                                    summary = "Creazione di una certificazione fittizia",
                                    value = """
                    {
                      "name": "Certificazione Bio",
                      "description": "Certificazione biologica per prodotti agricoli",
                      "issuing_body": "Issuing body di prova 3"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Certificazione creata con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<OperationResponse> createCertification(@Valid @RequestBody CertificationPayload certification){
        OperationResponse operationResponse = certificationService.createCertificationRequest(certification);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationResponse);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Elimina una certificazione",
            description = "Elimina una certificazione dato il suo ID."
    )
    @ApiResponse(responseCode = "200", description = "Certificazione eliminata con successo")
    @ApiResponse(responseCode = "404", description = "Certificazione non trovata")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<OperationResponse> deleteCertificationById(@PathVariable int id){
        OperationResponse response =  certificationService.deleteCertificationRequest(new DeletePayload(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    @Operation(
            summary = "Associa una certificazione a un prodotto",
            description = "Aggiunge una certificazione esistente a un prodotto specificato."
    )
    @ApiResponse(responseCode = "200", description = "Certificazione aggiunta con successo")
    @ApiResponse(responseCode = "404", description = "Certificazione o prodotto non trovato")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<String> addCertificationToProduct(@RequestBody AddProductCertificationRequest request){
        certificationService.addCertificationToProduct(request);
        return ResponseEntity.ok("Certificazione aggiunta al prodotto con successo");
    }
}