package it.unicam.cs.agritrace.controller;


import it.unicam.cs.agritrace.dtos.responses.CultivationMethodResponse;
import it.unicam.cs.agritrace.model.CultivationMethod;
import it.unicam.cs.agritrace.service.CultivationMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cultivation-method")
public class CultivationMethodController {
    private final CultivationMethodService cultivationMethodService;

    public CultivationMethodController(CultivationMethodService cultivationMethodService) {
        this.cultivationMethodService = cultivationMethodService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<CultivationMethodResponse>> getCultivationMethodAll(){
        List<CultivationMethodResponse> cultivionMethod = cultivationMethodService.getCultivationMethodAll();
        return ResponseEntity.ok(cultivionMethod);
    }
}



