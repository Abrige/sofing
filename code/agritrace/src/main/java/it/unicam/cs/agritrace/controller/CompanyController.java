package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.CompanyDTO;
import it.unicam.cs.agritrace.dtos.common.ReviewRequestDTO;
import it.unicam.cs.agritrace.dtos.requests.AddCompanyRequest;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.service.CompanyService;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;
    private final RequestService requestService;

    public CompanyController(CompanyService companyService,
                             RequestService requestService) {
        this.companyService = companyService;
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        return ResponseEntity.ok().body(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable int id) {
        return ResponseEntity.ok().body(companyService.getCompanyByIdDto(id));
    }

    // Richiesta di creazione di azienda da parte del TRASFORMATORE O PRODUTTORE
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
    @PostMapping
    public ResponseEntity<OperationResponse> addCompany(
            @RequestBody AddCompanyRequest addCompanyRequest) {

        OperationResponse response = companyService.addCompanyRequest(addCompanyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Approvazione richiesta di creazione azienda da parte del Gestore
    @PreAuthorize("hasRole('GESTORE_DELLA_PIATTAFORMA')")
    @PostMapping("/review")
    public ResponseEntity<Void> reviewCompanyRequest(@RequestBody ReviewRequestDTO companyRequest)  {
        requestService.reviewRequest(companyRequest);
        return ResponseEntity.ok().build();
    }


}
