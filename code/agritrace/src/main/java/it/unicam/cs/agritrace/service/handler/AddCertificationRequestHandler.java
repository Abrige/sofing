package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.CertificationPayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.repository.CertificationRepository;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AddCertificationRequestHandler implements RequestHandler{
    private final RequestType supportedRequestType;
    private final PayloadMapper payloadMapper;
    private final CertificationRepository certificationRepository;

    public AddCertificationRequestHandler(RequestTypeRepository requestTypeRepository, PayloadMapper payloadMapper, CertificationRepository certificationRepository) {
        this.supportedRequestType = requestTypeRepository.findByName("ADD_CERTIFICATION")
                .orElseThrow(() -> new ResourceNotFoundException("RequestType ADD_CERTIFICATION non trovato"));
        this.payloadMapper = payloadMapper;
        this.certificationRepository = certificationRepository;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return supportedRequestType;

    }

    @Override
    @Transactional
    public void handle(Request request) {
        // 1. Converto JSON in DTO
        CertificationPayload payload = payloadMapper.mapPayload(request, CertificationPayload.class);

        // 2. Crea la certificazione
        Certification newCert = new Certification();
        newCert.setName(payload.name());
        if(payload.description() != null) {
            newCert.setDescription(payload.description());
        }
        newCert.setIssuingBody(payload.issuingBody());
        newCert.setIsActive(true);

        // 3. Salvo la certificazione
        Certification saved = certificationRepository.save(newCert);
        // 5. Aggiorno la request col targetId
        request.setTargetId(saved.getId());
    }
}
