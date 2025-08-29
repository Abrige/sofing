package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.AddPackagePayload;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.service.PackageService;
import org.springframework.stereotype.Component;

@Component
public class AddPackageRequestHandler implements RequestHandler<AddPackagePayload> {

    private final PackageService packageService;
    private final PayloadMapper payloadMapper;

    public AddPackageRequestHandler(PackageService packageService, PayloadMapper payloadMapper) {
        this.packageService = packageService;
        this.payloadMapper = payloadMapper;
    }

    @Override
    public void handle(Request request) {
        // Converte il payload JSON in DTO
        AddPackagePayload payload = payloadMapper.mapPayload(request, AddPackagePayload.class);

        // Controlla lo status della request
        if(request.getStatus().getName()) {
            packageService.createPackage(payload);
        } else if(request.getStatus().isRejected()) {
            // Qui puoi gestire eventuali rollback, notifiche, ecc.
            packageService.rejectPackageCreation(payload);
        }
    }

    @Override
    public String getRequestTypeName() {
        return "ADD_PACKAGE";
    }
}
