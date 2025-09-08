package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.common.CompanyDTO;
import it.unicam.cs.agritrace.dtos.common.ReviewRequestDTO;
import it.unicam.cs.agritrace.dtos.requests.AddCompanyRequest;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.service.CompanyService;
import it.unicam.cs.agritrace.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@Tag(name = "Company", description = "Gestione delle aziende e delle richieste di creazione azienda")
public class CompanyController {

    private final CompanyService companyService;
    private final RequestService requestService;

    public CompanyController(CompanyService companyService,
                             RequestService requestService) {
        this.companyService = companyService;
        this.requestService = requestService;
    }

    @PreAuthorize("hasRole('GESTORE_DELLA_PIATTAFORMA')")
    @GetMapping
    @Operation(
            summary = "Ottiene la lista di tutte le aziende",
            description = "Restituisce i dettagli di tutte le aziende." +
            "Può essere utlizzato solo da Gestore della piattaforma"
            )
    @ApiResponse(responseCode = "200", description = "Lista recuperata con successo")
    @ApiResponse(responseCode = "401", description = "Accesso negato")
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        return ResponseEntity.ok().body(companyService.getAllCompanies());
    }

    @PreAuthorize("hasRole('GESTORE_DELLA_PIATTAFORMA')")
    @GetMapping("/{id}")
    @Operation(
            summary = "Ottiene la lista di una azienda specifica",
            description = "Restituisce i dettagli di una azienda." +
                    "Può essere utlizzato solo da Gestore della piattaforma"
    )
    @ApiResponse(responseCode = "200", description = "Azienda recuperata con successo")
    @ApiResponse(responseCode = "401", description = "Accesso negato")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable int id) {
        return ResponseEntity.ok().body(companyService.getCompanyByIdDto(id));
    }

    // Richiesta di creazione di azienda da parte del TRASFORMATORE O PRODUTTORE
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
    @PostMapping
    @Operation(summary = "Crea una nuova richiesta di registrazione azienda",
            description = "Permette ad una azienda di iniziare il prcesso di registrazione" +
                    "Richiesta effettuata da un PRODUTTORE o TRASFORMATORE",
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
                      "name": "Company di prova",
                      "fiscal_code": "fiscalcodediprova",
                      "location_id": 1,
                      "description": "descrizione generica",
                      "company_type_id": 1
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Azienda recuperata con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Valori forniti incompleti o errati")
    public ResponseEntity<OperationResponse> addCompany(
            @RequestBody @Valid AddCompanyRequest addCompanyRequest) {

        OperationResponse response = companyService.addCompanyRequest(addCompanyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    // Approvazione richiesta di creazione azienda da parte del Gestore
    @PreAuthorize("hasRole('GESTORE_DELLA_PIATTAFORMA')")
    @PostMapping("/review")
    @Operation(summary = "Crea una nuova richiesta di registrazione azienda",
            description = "Permette ad una azienda di iniziare il prcesso di registrazione" +
                    "Richiesta effettuata da un Gestore della piattaofrma",
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
                      "name": "Company di prova",
                      "fiscal_code": "fiscalcodediprova",
                      "location_id": 1,
                      "description": "descrizione generica",
                      "company_type_id": 1
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Azienda recuperata con successo")
    @ApiResponse(responseCode = "401", description = "Accesso negato")
    @ApiResponse(responseCode = "404", description = "Valori forniti incompleti o errati")
    public ResponseEntity<Void> reviewCompanyRequest(@RequestBody ReviewRequestDTO companyRequest)  {
        requestService.reviewRequest(companyRequest);
        return ResponseEntity.ok().build();
    }


}
