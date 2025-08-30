package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddPackagePayload;
import it.unicam.cs.agritrace.mappers.PackageMapper;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import org.springframework.stereotype.Service;

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

    public PackageService(ProductRepository productRepository, TypicalPackageRepository packageRepository, TypicalPackageRepository typicalPackageRepository,
                          TypicalPackageRepository typicalPackageRepository1, TypicalPackageItemRepository typicalPackageItemRepository, CompanyRepository companyRepository,
                          UserRepository userRepository,
                          DbTableRepository dbTableRepository,
                          PayloadMapper payloadMapper,
                          RequestRepository requestRepository) {
        this.productRepository = productRepository;
        this.typicalPackageRepository = typicalPackageRepository1;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.dbTableRepository = dbTableRepository;
        this.payloadMapper = payloadMapper;
        this.requestRepository = requestRepository;
    }

    public void createPackageRequest(AddPackagePayload addPackagePayload) {
        if (addPackagePayload.items().size() < 3) {
            throw new IllegalArgumentException("Devi selezionare almeno 3 prodotti");
        }

        User user = userRepository.findById(1).orElseThrow();
        Request entityRequest = new Request();
        entityRequest.setRequester(user);
        DbTable table = dbTableRepository.findDbTableByName("TYPICAL_PACKAGES").orElseThrow();
        entityRequest.setTargetTable(table);

        entityRequest.setPayload(payloadMapper.toJson(addPackagePayload));
        requestRepository.save(entityRequest);
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
