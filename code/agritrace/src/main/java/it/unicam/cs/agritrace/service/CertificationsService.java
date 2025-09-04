package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.responses.CertificationsResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.repository.CertificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationsService {
    private final CertificationRepository certificationRepository;

    public CertificationsService(CertificationRepository certificationRepository) {
        this.certificationRepository = certificationRepository;
    }

    public List<CertificationsResponse> GetCertifications(){
        List<Certification> AllCert = certificationRepository.findAllByIsActiveTrue();

        List<CertificationsResponse> AllCertResponse = AllCert.stream().map(
                Item -> new CertificationsResponse( Item.getId(),
                        Item.getName(),
                        Item.getDescription(),
                        Item.getIssuingBody())
        ).toList();


        return AllCertResponse;

    }

    public CertificationsResponse GetCertificationsById(int id) {

        Certification cert = certificationRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificazione non trovata: " + id));

        return new CertificationsResponse(
                cert.getId(),
                cert.getName(),
                cert.getDescription(),
                cert.getIssuingBody()
        );
    }

}