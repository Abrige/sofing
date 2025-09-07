package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.common.ReviewRequestDTO;
import it.unicam.cs.agritrace.dtos.responses.RequestResponse;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('CURATORE')")
@RestController
@RequestMapping("/api/requests")
@Tag(name = "Richieste", description = "Gestione delle richieste nel sistema")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping
    @Operation(
            summary = "Recupera tutte le richieste",
            description = "Ritorna la lista di tutte le richieste presenti nel sistema, con possibilità di filtrare per stato"
    )
    @ApiResponse(responseCode = "200", description = "Richieste recuperate con successo")
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

<<<<<<< Updated upstream
    //Ritorna le richieste che hanno statusName : pending
    @GetMapping("/pending")
    @Operation(
            summary = "Richieste in attesa",
            description = "Ritorna tutte le richieste che hanno stato 'PENDING' e sono in attesa di revisione"
    )
    @ApiResponse(responseCode = "200", description = "Richieste pending recuperate con successo")
    public ResponseEntity<List<RequestResponse>> getPendingApproval() {
        List<RequestResponse> pendingRequests = requestService.getAllPendingRequests();
        return ResponseEntity.ok(pendingRequests);
    }

=======
>>>>>>> Stashed changes
    //Approvazione richiesta
    @PostMapping("/review")
    @Operation(
            summary = "Revisione richiesta",
            description = "Permette al CURATORE di approvare o rifiutare una richiesta",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati per la revisione della richiesta",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio revisione",
                                    summary = "Esempio payload revisione richiesta",
                                    value = """
                    {
                      "request_id":48,
                      "action": "accept",
                      "decision_notes": "de tocca"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Richiesta revisionata con successo")
    @ApiResponse(responseCode = "400", description = "Input non valido")
    public ResponseEntity<Void> reviewRequest(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO)  {
        requestService.reviewRequest(reviewRequestDTO);
        return ResponseEntity.ok().build();
    }

}
