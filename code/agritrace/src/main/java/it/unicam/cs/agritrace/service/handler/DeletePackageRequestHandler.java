package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.model.TypicalPackage;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.repository.TypicalPackageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletePackageRequestHandler implements RequestHandler {

    private final RequestType requestType;
    private final PayloadMapper payloadMapper;
    private final TypicalPackageRepository typicalPackageRepository;

    public DeletePackageRequestHandler(RequestTypeRepository requestTypeRepository,
                                       PayloadMapper payloadMapper,
                                       TypicalPackageRepository typicalPackageRepository) {
        this.requestType = requestTypeRepository.findByName("DELETE_PACKAGE")
                .orElseThrow(() -> new ResourceNotFoundException("RequestType DELETE_PACKAGE non trovato"));
        this.payloadMapper = payloadMapper;
        this.typicalPackageRepository = typicalPackageRepository;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return requestType;
    }

    @Transactional
    @Override
    public void handle(Request request) {
        DeletePayload payload = payloadMapper.mapPayload(request, DeletePayload.class);

        TypicalPackage pkg = typicalPackageRepository.findByIdAndIsActiveTrue(payload.targetId())
                .orElseThrow(() -> new ResourceNotFoundException("Typical package non trovato"));
        pkg.setIsActive(false); // soft delete
        TypicalPackage saved = typicalPackageRepository.save(pkg);

        request.setTargetId(saved.getId());
    }
}
