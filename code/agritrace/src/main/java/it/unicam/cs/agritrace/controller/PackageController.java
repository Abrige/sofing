package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
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

@PreAuthorize("hasRole('DISTRIBUTORE_DI_TIPICITA')")
@RestController
@RequestMapping("/api/packages")
public class PackageController {
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public ResponseEntity<List<PackageDTO>> getAllPackages() {
        return ResponseEntity.ok(packageService.getPackages());
    }

    // –––––––––––––––––––––––––– CREATE PACKAGE ––––––––––––––––––––––––––
    // POST /api/packages
    @PostMapping()
    public ResponseEntity<OperationResponse> createPackage(
            @Valid @ValidPackageCreate @RequestBody PackagePayload request) {
        OperationResponse operationResponse = packageService.createPackageRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(operationResponse);
    }

    // –––––––––––––––––––––––––– DELETE PACKAGE ––––––––––––––––––––––––––
    // DELETE /api/packages/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<OperationResponse> deletePackage(@PathVariable int id) {
        OperationResponse operationResponse = packageService.deletePackageRequest(new DeletePayload(id));
        // si ritorna questo stato in caso di delete andata a buon fine
        return ResponseEntity.ok().body(operationResponse);
    }

    // –––––––––––––––––––––––––– UPDATE PACKAGE ––––––––––––––––––––––––––
    // PUT /api/packages
    @PutMapping
    public ResponseEntity<OperationResponse> updatePackage(
            @Valid @ValidPackageUpdate @RequestBody PackagePayload packagePayload) {
        OperationResponse operationResponse = packageService.updatePackageRequest(packagePayload);
        return ResponseEntity.ok(operationResponse);
    }
}

