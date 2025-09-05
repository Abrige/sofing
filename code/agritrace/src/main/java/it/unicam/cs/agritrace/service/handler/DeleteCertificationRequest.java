package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.repository.CertificationRepository;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.service.RequestService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteCertificationRequest implements RequestHandler {
    private final RequestType supportedRequestType;
    private final PayloadMapper payloadMapper;
    private final CertificationRepository certificationRepository;

    public DeleteCertificationRequest(RequestTypeRepository requestTypeRepository, PayloadMapper payloadMapper, CertificationRepository certificationRepository, RequestService requestService) {
        this.supportedRequestType = requestService.getRequestTypeByName("CERTIFICATION");
        this.payloadMapper = payloadMapper;
        this.certificationRepository = certificationRepository;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return supportedRequestType;
    }

    @Transactional
    @Override
    public void handle(Request request) {
        DeletePayload payload = payloadMapper.mapPayload(request, DeletePayload.class);
        Certification certification = certificationRepository.findById(payload.targetId()).orElseThrow(() -> new ResourceNotFoundException("Certification not found with id=" + payload.targetId()));
        certification.setIsActive(false); // soft delete
        Certification saved = certificationRepository.save(certification);

        request.setTargetId(saved.getId());

    }
}
