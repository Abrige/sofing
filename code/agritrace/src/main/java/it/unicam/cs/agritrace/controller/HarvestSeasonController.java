
package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.responses.HarvestSeasonResponse;
import it.unicam.cs.agritrace.service.HarvestSeasonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/harvestseason")
public class HarvestSeasonController {
    private final HarvestSeasonService harvestSeasonService;

    public HarvestSeasonController(HarvestSeasonService harvestSeasonService){
        this.harvestSeasonService = harvestSeasonService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<HarvestSeasonResponse>> getAllHarvestSeason(){
        return ResponseEntity.ok(harvestSeasonService.GetAllHarvestSeason());
    }

}