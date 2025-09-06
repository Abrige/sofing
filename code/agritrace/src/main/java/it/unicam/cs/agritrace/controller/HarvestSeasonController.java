
package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.responses.HarvestSeasonResponse;
import it.unicam.cs.agritrace.service.HarvestSeasonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/harvestseason")
@Tag(name = "Stagioni di raccolta", description = "Gestione delle stagioni di raccolta")
public class HarvestSeasonController {
    private final HarvestSeasonService harvestSeasonService;

    public HarvestSeasonController(HarvestSeasonService harvestSeasonService){
        this.harvestSeasonService = harvestSeasonService;
    }
    /**
     * Restituisce tutte le stagioni di raccolta disponibili.
     */
    @GetMapping("/list")
    @Operation(
            summary = "Lista stagioni di raccolta",
            description = "Ritorna tutte le stagioni di raccolta registrate nel sistema."
    )
    @ApiResponse(responseCode = "200", description = "Stagioni di raccolta recuperate con successo")
    public ResponseEntity<List<HarvestSeasonResponse>> getAllHarvestSeason(){
        return ResponseEntity.ok(harvestSeasonService.GetAllHarvestSeason());
    }
    /**
     * Restituisce una stagione di raccolta specifica dato l'ID.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera stagione di raccolta per ID",
            description = "Restituisce i dettagli di una stagione di raccolta specifica dato il suo identificativo."
    )
    @ApiResponse(responseCode = "200", description = "Stagione di raccolta recuperata con successo")
    @ApiResponse(responseCode = "404", description = "Stagione di raccolta non trovata")
    public ResponseEntity<HarvestSeasonResponse> getHarvestSeasonId(@PathVariable int id){
        return ResponseEntity.ok(harvestSeasonService.GetHarvestSeasonId(id));
    }

}