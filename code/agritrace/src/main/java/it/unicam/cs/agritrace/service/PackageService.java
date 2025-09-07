package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.payloads.PackageItemPayload;
import it.unicam.cs.agritrace.dtos.payloads.PackageCreateUpdatePayload;
import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.InvalidPackageRequestException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PackageMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.repository.TypicalPackageRepository;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {
    private final TypicalPackageRepository typicalPackageRepository;
    private final RequestRepository requestRepository;
    private final RequestFactory requestFactory;
    private final ProductService productService;
    private final UserService userService;
    private final RequestService requestService;
    private final CompanyRepository companyRepository;

    public PackageService(
            RequestRepository requestRepository,
            TypicalPackageRepository typicalPackageRepository1,
            RequestFactory requestFactory,
            ProductService productService,
            UserService userService,
            RequestService requestService,
            CompanyRepository companyRepository) {
        this.requestRepository = requestRepository;
        this.typicalPackageRepository = typicalPackageRepository1;
        this.requestFactory = requestFactory;
        this.productService = productService;
        this.userService = userService;
        this.requestService = requestService;
        this.companyRepository = companyRepository;
    }

    // crea una richiesta di creazione di un pacchetto
    public OperationResponse createPackageRequest(PackageCreateUpdatePayload payload) {
        User requester = getAuthenticatedUser();

        // Recupero l’azienda dell’utente
        Company company = companyRepository.findByOwnerIdAndIsDeletedFalse(requester.getId())
                .orElseThrow(() -> new AccessDeniedException("L'utente non ha nessuna azienda associata"));

        // Controllo che tutti i prodotti esistano nel database
        for (PackageItemPayload item : payload.items()) {
            if(!productService.existsProductById(item.productId())){
                throw new InvalidPackageRequestException("Prodotto non trovato con id: " + item.productId());
            }
        }

        PackagePayload packageWithProducer = new PackagePayload(
                payload.packageId(),
                payload.name(),
                payload.description(),
                payload.price(),
                company.getId(),
                payload.items()
        );


        Request request = requestFactory.createRequest(
                "TYPICAL_PACKAGES",
                "ADD_PACKAGE",
                packageWithProducer,
                requester
        );

        Request savedRequest = requestService.saveRequest(request);

        return new OperationResponse(savedRequest.getId(), "PACKAGE", "CREATE", savedRequest.getCreatedAt());
    }

    // crea una richiesta di cancellazione di un pacchetto
    public OperationResponse deletePackageRequest(DeletePayload payload) {

        TypicalPackage aPackage = getPackageById(payload.targetId());

        User requester = getAuthenticatedUser();

        // Prende la company dell'utente
        Company company = companyRepository.findByOwnerIdAndIsDeletedFalse(requester.getId())
                .orElseThrow(() -> new AccessDeniedException("L'utente non ha nessuna azienda associata"));

        // Se il produttore di quel prodotto non è uguale al produttore che si sta cercando di modificare cioè quello
        // per cui si fa la richiesta di modifica
        if (!aPackage.getProducer().getId().equals(company.getId())) {
            throw new AccessDeniedException("Non puoi eliminare prodotti che non appartengono alla tua azienda");
        }

        Request request = requestFactory.createRequest(
                "TYPICAL_PACKAGES",
                "DELETE_PACKAGE",
                payload,
                requester
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PACKAGE", "DELETE", savedRequest.getCreatedAt());
    }

    // crea una richiesta di update di un pacchetto
    public OperationResponse updatePackageRequest(PackageCreateUpdatePayload payload) {

        User requester = getAuthenticatedUser();

        // Recupero la company dell'utente
        Company company = companyRepository.findByOwnerIdAndIsDeletedFalse(requester.getId())
                .orElseThrow(() -> new AccessDeniedException("L'utente non ha nessuna azienda associata"));

        // Recupero il prodotto esistente
        TypicalPackage existingPackage = getPackageById(payload.packageId());

        // Controllo che il prodotto appartenga alla company dell'utente
        if (!existingPackage.getProducer().getId().equals(company.getId())) {
            throw new AccessDeniedException("Non puoi modificare prodotti che non appartengono alla tua azienda");
        }

        List<PackageItemPayload> items = payload.items();

        // se la lista è null o vuota, non facciamo controlli sugli items
        if (items != null && !items.isEmpty()) {
            // controllo che ci siano almeno 3 prodotti
            if (items.size() < 3) {
                throw new IllegalArgumentException("Il pacchetto deve contenere almeno 3 prodotti");
            }

            // controllo che tutti i prodotti esistano
            for (PackageItemPayload item : items) {
                if (!productService.existsProductById(item.productId())) {
                    throw new InvalidPackageRequestException("Prodotto non trovato con id: " + item.productId());
                }
            }
        }

        Request request = requestFactory.createRequest(
                "TYPICAL_PACKAGES",
                "UPDATE_PACKAGE",
                payload,
                requester
        );
        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PACKAGE", "UPDATE", savedRequest.getCreatedAt());
    }

    public List<PackageDTO> getPackages() {
        return typicalPackageRepository.findAll().stream()
                .map(PackageMapper::toDTO)
                .toList();
    }

    public PackageDTO getPackageDTOById(Integer id) {
        TypicalPackage pkg = typicalPackageRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Il package con id=" + id + " non è stato trovato"));

        return PackageMapper.toDTO(pkg);
    }


    public TypicalPackage savePackage(TypicalPackage pkg) {
        return typicalPackageRepository.save(pkg);
    }

    public TypicalPackage getPackageById(Integer id){
        return typicalPackageRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Il package con " +
                "id=" + id + " non trovato"));
    }

    public boolean existsPackageById(Integer id) {
        return typicalPackageRepository.existsByIdAndIsActiveTrue(id);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }
}
