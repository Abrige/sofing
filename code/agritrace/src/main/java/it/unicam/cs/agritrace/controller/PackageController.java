package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.PackageCreationRequest;
import it.unicam.cs.agritrace.dtos.responses.PackageResponse;
import it.unicam.cs.agritrace.service.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
public class PackageController {
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping()
    public ResponseEntity<PackageResponse> createPackage(@RequestBody PackageCreationRequest request) {
        PackageResponse created = packageService.createPackage(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    @RequestMapping("/packages")
    public ResponseEntity<List<PackageResponse>> getAllPackages() {
        return new ResponseEntity<>(packageService.getPackages(), HttpStatus.OK);
    }
}
