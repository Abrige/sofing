package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddPackagePayload;
import it.unicam.cs.agritrace.exceptions.DbTableNotFoundException;
import it.unicam.cs.agritrace.exceptions.InvalidPackageRequestException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.exceptions.UserNotFoundException;
import it.unicam.cs.agritrace.mappers.PackageMapper;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PackageService {
    private final ProductRepository productRepository;
    private final TypicalPackageRepository typicalPackageRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final DbTableRepository dbTableRepository;
    private final PayloadMapper payloadMapper;
    private final RequestRepository requestRepository;
    private final RequestTypeRepository requestTypeRepository;
    private final StatusRepository statusRepository;

    public PackageService(ProductRepository productRepository, TypicalPackageRepository packageRepository, TypicalPackageRepository typicalPackageRepository,
                          TypicalPackageRepository typicalPackageRepository1, TypicalPackageItemRepository typicalPackageItemRepository, CompanyRepository companyRepository,
                          UserRepository userRepository,
                          DbTableRepository dbTableRepository,
                          PayloadMapper payloadMapper,
                          RequestRepository requestRepository,
                          RequestTypeRepository requestTypeRepository,
                          StatusRepository statusRepository) {
        this.productRepository = productRepository;
        this.typicalPackageRepository = typicalPackageRepository1;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.dbTableRepository = dbTableRepository;
        this.payloadMapper = payloadMapper;
        this.requestRepository = requestRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.statusRepository = statusRepository;
    }

    public void createPackageRequest(AddPackagePayload payload) {
        // Validazione business
        if (payload.items() == null || payload.items().size() < 3) {
            throw new InvalidPackageRequestException("Il pacchetto deve contenere almeno 3 prodotti");
        }

        // TODO: sostituire con utente loggato (security context)
        Integer requesterId = 1;
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new UserNotFoundException(requesterId));

        // TODO: spostare in enum / costante
        Integer typicalPackagesTableId = 23;
        DbTable targetTable = dbTableRepository.findById(typicalPackagesTableId)
                .orElseThrow(() -> new DbTableNotFoundException(typicalPackagesTableId));

        // Creo la request
        Request request = new Request();
        request.setRequester(requester);
        request.setTargetTable(targetTable);
        request.setPayload(payloadMapper.toJson(payload));
        request.setCreatedAt(Instant.now());
        Integer requestTypeId = 2;
        RequestType requestType = requestTypeRepository.findById(requestTypeId).orElseThrow(() -> new ResourceNotFoundException("Tipo di richiesta non trovata sul db")); // ADD_PACKAGE
        request.setRequestType(requestType);
        Integer statusId = 2; // PENDING status
        Status status = statusRepository.findById(statusId).orElseThrow(() -> new ResourceNotFoundException("Status non trovato sul db"));
        request.setStatus(status);

        requestRepository.save(request);
    }

    public List<PackageDTO> getPackages() {
        return typicalPackageRepository.findAll().stream()
                .map(PackageMapper::toDTO)
                .toList();
    }

    public Company findCompanyById(int id) {
        return companyRepository.findById(id).orElseThrow();
    }

    public TypicalPackage savePackage(TypicalPackage pkg) {
        return typicalPackageRepository.save(pkg);
    }
}
