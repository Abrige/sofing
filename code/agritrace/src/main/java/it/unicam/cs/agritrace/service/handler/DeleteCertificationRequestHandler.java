package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.service.CertificationService;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteCertificationRequestHandler implements RequestHandler {
    private final RequestType supportedRequestType;
    private final PayloadMapper payloadMapper;
    private final CertificationService certificationService;

    public DeleteCertificationRequestHandler(PayloadMapper payloadMapper,
                                             CertificationService certificationService,
                                             RequestTypeRepository requestTypeRepository) {
        this.supportedRequestType = requestTypeRepository.findByName("DELETE_CERTIFICATION").orElseThrow(() -> new ResourceNotFoundException(
                "Request type non trovato DELETE_CERTIFICATION"));
        this.payloadMapper = payloadMapper;
        this.certificationService = certificationService;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return supportedRequestType;
    }

    @Transactional
    @Override
    public void handle(Request request) {
        DeletePayload payload = payloadMapper.mapPayload(request, DeletePayload.class);
        Certification certification = certificationService.getCertificationById(payload.targetId());
        certification.setIsActive(false); // soft delete
        Certification saved = certificationService.saveCertification(certification);

        request.setTargetId(saved.getId());

    }
}
