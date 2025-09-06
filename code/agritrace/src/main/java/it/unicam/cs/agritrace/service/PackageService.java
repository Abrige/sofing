package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.dtos.payloads.PackageItemPayload;
import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.InvalidPackageRequestException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PackageMapper;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.TypicalPackage;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.repository.TypicalPackageRepository;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
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

    public PackageService(
            RequestRepository requestRepository,
            TypicalPackageRepository typicalPackageRepository1,
            RequestFactory requestFactory,
            ProductService productService,
            UserService userService) {
        this.requestRepository = requestRepository;
        this.typicalPackageRepository = typicalPackageRepository1;
        this.requestFactory = requestFactory;
        this.productService = productService;
        this.userService = userService;
    }

    // crea una richiesta di creazione di un pacchetto
    public OperationResponse createPackageRequest(PackagePayload payload) {
        if (payload.items() == null || payload.items().size() < 3) {
            throw new InvalidPackageRequestException("Il pacchetto deve contenere almeno 3 prodotti");
        }

        // Controllo che tutti i prodotti esistano nel database
        for (PackageItemPayload item : payload.items()) {
            if(!productService.existsProductById(item.productId())){
                throw new InvalidPackageRequestException("Prodotto non trovato con id: " + item.productId());
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

        Request request = requestFactory.createRequest(
                "TYPICAL_PACKAGES",
                "ADD_PACKAGE",
                payload,
                requester
        );

        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "PACKAGE", "CREATE", savedRequest.getCreatedAt());
    }

    // crea una richiesta di cancellazione di un pacchetto
    public OperationResponse deletePackageRequest(DeletePayload payload) {

        if(!existsPackageById(payload.targetId())){
            throw new InvalidPackageRequestException("Prodotto non trovato con id: " + payload.targetId());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

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
    public OperationResponse updatePackageRequest(PackagePayload payload) {
        if (payload.packageId() == null) {
            throw new IllegalArgumentException("productId è obbligatorio per l'update");
        }

        // Controllo che esista il pacchetto
        if(!existsPackageById(payload.packageId())){
            throw new InvalidPackageRequestException("Prodotto non trovato con id: " + payload.packageId());
        }

        // Controllo che tutti i prodotti esistano nel database
        for (PackageItemPayload item : payload.items()) {
            if(!productService.existsProductById(item.productId())){
                throw new InvalidPackageRequestException("Prodotto non trovato con id: " + item.productId());
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

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
        TypicalPackage pkg = typicalPackageRepository.findById(id)
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
}
