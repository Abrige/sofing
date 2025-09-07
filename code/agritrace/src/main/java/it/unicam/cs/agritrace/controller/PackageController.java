package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.payloads.PackageCreateUpdatePayload;
<<<<<<< Updated upstream
import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
=======
>>>>>>> Stashed changes
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.service.PackageService;
import it.unicam.cs.agritrace.validators.create.ValidPackageCreate;
import it.unicam.cs.agritrace.validators.update.ValidPackageUpdate;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@Tag(name = "Pacchetti tipici", description = "Gestione dei pacchetti tipici nel sistema")
public class PackageController {
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @Operation(
            summary = "Lista pacchetti tipici",
            description = "Ritorna tutti i pacchetti tipici registrati nel sistema"
    )
    @ApiResponse(responseCode = "200", description = "Pacchetti recuperati con successo")
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        return ResponseEntity.ok(packageService.getPackages());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera pacchetto per ID",
            description = "Ritorna i dettagli di un pacchetto tipico dato il suo ID"
    )
    @ApiResponse(responseCode = "200", description = "Pacchetto recuperato con successo")
    @ApiResponse(responseCode = "404", description = "Pacchetto non trovato")
    public ResponseEntity<PackageDTO> getPackageById(@PathVariable int   id){
        return ResponseEntity.ok(packageService.getPackageDTOById(id));
    }

    // –––––––––––––––––––––––––– CREATE PACKAGE ––––––––––––––––––––––––––
    // POST /api/packages
    @PreAuthorize("hasRole('DISTRIBUTORE_DI_TIPICITA')")
    @PostMapping()
    @Operation(
            summary = "Crea un nuovo pacchetto tipico",
            description = "Crea un pacchetto tipico nel sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati del pacchetto da creare",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Pacchetto esempio",
                                    summary = "Esempio di creazione pacchetto tipico",
                                    value = """
                    {
                     "name": "Prova con utente",
                     "description": "prova utente",
                     "price": 1,
                     "producer_id": 1,
                     "items": [
                       {"product_id": 1, "quantity": 1},
                       {"product_id": 2, "quantity": 2},
                       {"product_id": 3, "quantity": 3}
                      ]
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Pacchetto creato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<OperationResponse> createPackage(
            @Valid @ValidPackageCreate @RequestBody PackageCreateUpdatePayload request) {
        OperationResponse operationResponse = packageService.createPackageRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationResponse);
    }
    // –––––––––––––––––––––––––– CREATE PACKAGE ––––––––––––––––––––––––––
    // POST /api/packages

    // –––––––––––––––––––––––––– DELETE PACKAGE ––––––––––––––––––––––––––
    // DELETE /api/packages/{id}
    @PreAuthorize("hasRole('DISTRIBUTORE_DI_TIPICITA')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Elimina un pacchetto tipico",
            description = "Elimina un pacchetto dato il suo ID"
    )
    @ApiResponse(responseCode = "200", description = "Pacchetto eliminato con successo")
    @ApiResponse(responseCode = "404", description = "Pacchetto non trovato")
    public ResponseEntity<OperationResponse> deletePackage(@PathVariable int id) {
        OperationResponse operationResponse = packageService.deletePackageRequest(new DeletePayload(id));
        // si ritorna questo stato in caso di delete andata a buon fine
        return ResponseEntity.ok().body(operationResponse);
    }

    // –––––––––––––––––––––––––– UPDATE PACKAGE ––––––––––––––––––––––––––
    // PUT /api/packages
    @PreAuthorize("hasRole('DISTRIBUTORE_DI_TIPICITA')")
    @PutMapping
    @Operation(
            summary = "Aggiorna un pacchetto tipico",
            description = "Aggiorna i dati di un pacchetto tipico esistente",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati del pacchetto da aggiornare",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Aggiorna pacchetto esempio",
                                    summary = "Esempio di aggiornamento pacchetto",
                                    value = """
                    {
                      "package_id": 11,
                      "name": "Pacco senza senso"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Pacchetto aggiornato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida")
    public ResponseEntity<OperationResponse> updatePackage(
            @Valid @ValidPackageUpdate @RequestBody PackageCreateUpdatePayload packageCreateUpdatePayload) {
        OperationResponse operationResponse = packageService.updatePackageRequest(packageCreateUpdatePayload);
        return ResponseEntity.ok(operationResponse);
    }
}

