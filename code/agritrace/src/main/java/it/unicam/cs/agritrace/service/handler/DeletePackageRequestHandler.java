package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.model.TypicalPackage;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.service.PackageService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletePackageRequestHandler implements RequestHandler {

    private final RequestType requestType;
    private final PayloadMapper payloadMapper;
    private final PackageService packageService;

    public DeletePackageRequestHandler(RequestTypeRepository requestTypeRepository,
                                       PayloadMapper payloadMapper,
                                       PackageService packageService) {
        this.requestType = requestTypeRepository.findByName("DELETE_PACKAGE")
                .orElseThrow(() -> new ResourceNotFoundException("RequestType DELETE_PACKAGE non trovato"));
        this.payloadMapper = payloadMapper;
        this.packageService = packageService;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return requestType;
    }

    @Transactional
    @Override
    public void handle(Request request) {
        DeletePayload payload = payloadMapper.mapPayload(request, DeletePayload.class);

        TypicalPackage pkg = packageService.getPackageById(payload.targetId());
        pkg.setIsActive(false); // soft delete
        TypicalPackage saved = packageService.savePackage(pkg);

        request.setTargetId(saved.getId());
    }
}
