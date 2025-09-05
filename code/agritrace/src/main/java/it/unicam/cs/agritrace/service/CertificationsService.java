package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.payloads.CertificationPayload;
import it.unicam.cs.agritrace.dtos.requests.CertificationRequest;
import it.unicam.cs.agritrace.dtos.responses.CertificationsResponse;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.repository.CertificationRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificationsService {
    private final CertificationRepository certificationRepository;
    private final ProductRepository productRepository;
    private final RequestFactory requestFactory;
    private final RequestRepository requestRepository;

    public CertificationsService(CertificationRepository certificationRepository, ProductRepository productRepository, RequestFactory requestFactory, RequestRepository requestRepository) {
        this.certificationRepository = certificationRepository;
        this.productRepository = productRepository;
        this.requestFactory = requestFactory;
        this.requestRepository = requestRepository;
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

    @Transactional
    public OperationResponse createCertification(CertificationPayload certification) {
        Request request = requestFactory.createRequest(
                "CERTIFICATIONS",
                "ADD_CERT",
                certification
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "CERTIFICATIONS", "ADD_CERT", savedRequest.getCreatedAt());

    }

    public void deleteCertification(int id) {
        Optional<Certification> certification = certificationRepository.findByIdAndIsActiveTrue(id);
        certification.ifPresent(value -> value.setIsActive(false));
    }

    public void addCertificationToProduct(int certificationId, int productId) {
        Optional<Certification> certification = certificationRepository.findByIdAndIsActiveTrue(certificationId);
        Optional<Product> product = productRepository.findByIdAndIsDeletedFalse(productId);
        if (certification.isPresent() && product.isPresent()) {
            Product validProduct = product.get();
            Certification cert = certification.get();

        }
    }
}