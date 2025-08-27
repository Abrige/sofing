package it.unicam.cs.agritrace.service;
import it.unicam.cs.agritrace.dtos.common.PackageItemDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddPackagePayload;
import it.unicam.cs.agritrace.dtos.requests.PackageCreationRequest;
import it.unicam.cs.agritrace.dtos.responses.PackageResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.TypicalPackage;
import it.unicam.cs.agritrace.model.TypicalPackageItem;
import it.unicam.cs.agritrace.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PackageService {
    private final ProductRepository productRepository;
    private final TypicalPackageRepository typicalPackageRepository;
    private final TypicalPackageItemRepository typicalPackageItemRepository;
    private final CompanyRepository companyRepository;

    public PackageService(ProductRepository productRepository, TypicalPackageRepository packageRepository, TypicalPackageRepository typicalPackageRepository,
                          TypicalPackageRepository typicalPackageRepository1, TypicalPackageItemRepository typicalPackageItemRepository, CompanyRepository companyRepository) {
        this.productRepository = productRepository;
        this.typicalPackageRepository = typicalPackageRepository1;
        this.typicalPackageItemRepository = typicalPackageItemRepository;
        this.companyRepository = companyRepository;
    }

    public PackageResponse createPackage(PackageCreationRequest request) {
            // Step 2: recupera prodotti dal marketplace
            List<Product> products = productRepository.findAllById(
                    request.items()
                            .stream()
                            .map(PackageItemDTO::productId).toList());

            if (products.size() < 3) {
                throw new IllegalArgumentException("Devi selezionare almeno 3 prodotti");
            }
        Company producer = companyRepository.findById(request.producer_id())
                .orElseThrow(() -> new IllegalArgumentException("Producer non trovato con id: " + request.producer_id()));

//        for (PackageItemDTO product : request.items()) {
//            if (!productRepository.existsById(product.productId())) {
//                throw new IllegalArgumentException("Duplicated product");
//            }
//            Product prod = productRepository.getById(product.productId());
//        }
            //TODO stato = "IN_ATTESA"
            TypicalPackage pkg = new TypicalPackage();
            pkg.setName(request.name());
            pkg.setDescription(request.description());
            pkg.setPrice(request.price());
            pkg.setProducer(producer);

            Set<TypicalPackageItem> packageItemsList = new HashSet<>();
            for (PackageItemDTO dto : request.items()) {
                Product product = products.stream()
                        .filter(p -> p.getId().equals(dto.productId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato: " + dto.productId()));

                TypicalPackageItem packageItem = new TypicalPackageItem();
                packageItem.setProduct(product);
                packageItem.setQuantity(dto.quantity());
                packageItem.setTypicalPackage(pkg);
                packageItemsList.add(packageItem);
            }
            pkg.setTypicalPackageItems(packageItemsList);
        typicalPackageRepository.save(pkg);
            return new PackageResponse(pkg);
    }

    public List<PackageResponse> getPackages() {
        return typicalPackageRepository.findAll().stream().map(PackageResponse::new).toList();
    }
}
