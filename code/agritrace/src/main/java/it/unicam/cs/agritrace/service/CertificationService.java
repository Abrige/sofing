package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.payloads.CertificationPayload;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.requests.AddProductCertificationRequest;
import it.unicam.cs.agritrace.dtos.responses.CertificationResponse;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.CertificationRepository;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
    private final CompanyRepository companyRepository;

    public CertificationService(CertificationRepository certificationRepository, ProductRepository productRepository, RequestFactory requestFactory, RequestRepository requestRepository,
                                UserService userService,
                                CompanyRepository companyRepository) {
        this.certificationRepository = certificationRepository;
        this.productRepository = productRepository;
        this.requestFactory = requestFactory;
        this.requestRepository = requestRepository;
        this.userService = userService;
        this.companyRepository = companyRepository;
    }

    public List<CertificationResponse> getCertifications(){
        List<Certification> AllCert = certificationRepository.findAllByIsActiveTrue();

        List<CertificationResponse> AllCertResponse = AllCert.stream().map(
                Item -> new CertificationResponse( Item.getId(),
                        Item.getName(),
                        Item.getDescription(),
                        Item.getIssuingBody())
        ).toList();


        return AllCertResponse;

    }

    public CertificationResponse getCertificationsById(int id) {

        Certification cert = certificationRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificazione non trovata: " + id));

        return new CertificationResponse(
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

    public void addCertificationToProduct(AddProductCertificationRequest request) {
        User user = getAuthenticatedUser();

        // Recupero la company dell'utente
        Company company = companyRepository.findByOwnerAndIsDeletedFalse(user)
                .orElseThrow(() -> new AccessDeniedException("L'utente non ha nessuna azienda associata"));

        Product product = productRepository.findByIdAndIsDeletedFalse(request.productId())
                .orElseThrow(()-> new ResourceNotFoundException("Prodotto non trovata: " + request.productId()));
        if(!product.getProducer().equals(company)){
            throw new AccessDeniedException("Non puoi modificare le certificazioni del prodotto di un'altra azienda");
        }

        Certification certification = certificationRepository.findByIdAndIsActiveTrue(request.certificationId())
                .orElseThrow(() -> new ResourceNotFoundException("Certificazione non trovata: " + request.certificationId()));

        ProductCertification productCertification = new ProductCertification();
        productCertification.setProduct(product);
        productCertification.setCertification(certification);
        productCertification.setCertificateNumber(request.certificateNumber());
        productCertification.setIssueDate(request.issueDate());
        productCertification.setExpiryDate(request.expiryDate());

        // Aggiungo senza perdere quelle già presenti
        product.getProductCertifications().add(productCertification);

        // Salvo il prodotto aggiornato
        productRepository.save(product);

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

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }
}