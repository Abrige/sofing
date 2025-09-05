package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.payloads.CertificationPayload;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.responses.CertificationsResponse;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.CertificationRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final ProductRepository productRepository;
    private final RequestFactory requestFactory;
    private final RequestRepository requestRepository;
    private final UserService userService;

    public CertificationService(CertificationRepository certificationRepository, ProductRepository productRepository, RequestFactory requestFactory, RequestRepository requestRepository,
                                UserService userService) {
        this.certificationRepository = certificationRepository;
        this.productRepository = productRepository;
        this.requestFactory = requestFactory;
        this.requestRepository = requestRepository;
        this.userService = userService;
    }

    public List<CertificationsResponse> getCertifications(){
        List<Certification> AllCert = certificationRepository.findAllByIsActiveTrue();

        List<CertificationsResponse> AllCertResponse = AllCert.stream().map(
                Item -> new CertificationsResponse( Item.getId(),
                        Item.getName(),
                        Item.getDescription(),
                        Item.getIssuingBody())
        ).toList();


        return AllCertResponse;

    }

    public CertificationsResponse getCertificationsById(int id) {

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
    public OperationResponse createCertificationRequest(CertificationPayload certification) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email dall’utente loggato

        User requester = userService.getUserByEmail(email);

        Request request = requestFactory.createRequest(
                "CERTIFICATIONS",
                "ADD_CERTIFICATION",
                certification,
                requester
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "CERTIFICATIONS", "ADD", savedRequest.getCreatedAt());

    }

    public OperationResponse deleteCertificationRequest(DeletePayload deletePayload) {
        if(!existsCertificationById(deletePayload.targetId())){
            throw new ResourceNotFoundException("Certificazione non trovata: " + deletePayload.targetId());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

        Request request = requestFactory.createRequest(
                "CERTIFICATIONS",
                "DELETE_CERTIFICATION",
                deletePayload,
                requester
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "CERTIFICATION", "DELETE", savedRequest.getCreatedAt());

    }

    public void addCertificationToProduct(int certificationId, int productId) {
        Optional<Certification> certification = certificationRepository.findByIdAndIsActiveTrue(certificationId);
        Optional<Product> product = productRepository.findByIdAndIsDeletedFalse(productId);
        if (certification.isPresent() && product.isPresent()) {
            Product validProduct = product.get();
            Certification cert = certification.get();

        }
    }

    public boolean existsCertificationById(int certificationId) {
        return certificationRepository.existsByIdAndIsActiveTrue(certificationId);
    }

    public Certification getCertificationById(int certificationId) {
        return certificationRepository.findByIdAndIsActiveTrue(certificationId).orElseThrow(() -> new ResourceNotFoundException("Certificazione non trovata: " + certificationId));
    }

    public Certification saveCertification(Certification certification) {
        return certificationRepository.save(certification);
    }
}