package it.unicam.cs.agritrace.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.common.PackageDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddPackagePayload;
import it.unicam.cs.agritrace.mappers.PackageMapper;
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

    public PackageService(ProductRepository productRepository, TypicalPackageRepository packageRepository, TypicalPackageRepository typicalPackageRepository,
                          TypicalPackageRepository typicalPackageRepository1, TypicalPackageItemRepository typicalPackageItemRepository, CompanyRepository companyRepository,
                          UserRepository userRepository,
                          DbTableRepository dbTableRepository) {
        this.productRepository = productRepository;
        this.typicalPackageRepository = typicalPackageRepository1;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.dbTableRepository = dbTableRepository;
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
        entityRequest.setTargetId(null);
        entityRequest.setRequestType("c");

        // converto il DTO in stringa e lo metto nel campo corretto
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Converti il DTO in JSON e setta subito il payload
            entityRequest.setPayload(mapper.writeValueAsString(addPackagePayload));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante la serializzazione del DTO in JSON", e);
        }
    }

    public List<PackageDTO> getPackages() {
        return typicalPackageRepository.findAll().stream()
                .map(PackageMapper::toDTO)
                .toList();
    }
}
