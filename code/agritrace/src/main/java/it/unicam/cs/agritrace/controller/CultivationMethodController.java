package it.unicam.cs.agritrace.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.responses.CultivationMethodResponse;
import it.unicam.cs.agritrace.service.CultivationMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE')")
@RestController
@RequestMapping("/api/cultivation-method")
@Tag(name = "Metodi di coltivazione", description = "Gestione dei metodi di coltivazione disponibili")
public class CultivationMethodController {
    private final CultivationMethodService cultivationMethodService;

    public CultivationMethodController(CultivationMethodService cultivationMethodService) {
        this.cultivationMethodService = cultivationMethodService;
    }

    @GetMapping("/list")
    @Operation(
            summary = "Recupera tutti i metodi di coltivazione",
            description = "Ritorna la lista completa dei metodi di coltivazione disponibili nel sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista metodi di coltivazione recuperata con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    public ResponseEntity<List<CultivationMethodResponse>> getCultivationMethodAll(){
        List<CultivationMethodResponse> cultivationMethod = cultivationMethodService.getCultivationMethodAll();
        return ResponseEntity.ok(cultivationMethod);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera un metodo di coltivazione per ID",
            description = "Restituisce i dettagli di un metodo di coltivazione identificato dal suo ID."
    )
    @ApiResponse(responseCode = "200", description = "Metodo di coltivazione recuperato con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Metodo di coltivazione non trovato")
    public ResponseEntity<CultivationMethodResponse> getCultivationMethodById(@PathVariable Integer id){
        CultivationMethodResponse cultivationMethod = cultivationMethodService.getCultivationMethodById(id);

        return ResponseEntity.ok(cultivationMethod);
    }
}



