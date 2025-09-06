package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.repository.TypicalPackageRepository;
import it.unicam.cs.agritrace.service.CompanyService;
import it.unicam.cs.agritrace.service.PackageService;
import it.unicam.cs.agritrace.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UpdatePackageRequestHandler implements RequestHandler {

    private final RequestType requestType;
    private final PayloadMapper payloadMapper;
    private final CompanyService companyService;
    private final TypicalPackageRepository typicalPackageRepository;
    private final ProductRepository productRepository;

    public UpdatePackageRequestHandler(RequestTypeRepository requestTypeRepository,
                                       PayloadMapper payloadMapper,
                                       CompanyService companyService,
                                       TypicalPackageRepository typicalPackageRepository,
                                       ProductRepository productRepository) {
        this.requestType = requestTypeRepository.findByName("UPDATE_PACKAGE")
                .orElseThrow(() -> new ResourceNotFoundException("RequestType UPDATE_PACKAGE non trovato"));
        this.payloadMapper = payloadMapper;
        this.companyService = companyService;
        this.typicalPackageRepository = typicalPackageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return requestType;
    }

    @Transactional
    @Override
    public void handle(Request request) {
        PackagePayload payload = payloadMapper.mapPayload(request, PackagePayload.class);

        if (payload.packageId() == null) {
            throw new IllegalArgumentException("Per aggiornare un pacchetto, l'ID deve essere specificato.");
        }

        TypicalPackage pkg = typicalPackageRepository.findByIdAndIsActiveTrue(payload.packageId())
                .orElseThrow(() -> new ResourceNotFoundException("Typical package con id="+ payload.packageId() + " non trovato"));

        if (payload.name() != null) pkg.setName(payload.name());
        if (payload.description() != null) pkg.setDescription(payload.description());
        if (payload.price() != null) pkg.setPrice(payload.price());
        if (payload.producerId() != null) {
            pkg.setProducer(companyService.getCompanyById(payload.producerId()));
        }

        if (payload.items() != null && payload.items().size() >= 3) {
            Set<TypicalPackageItem> items = payload.items().stream()
                    .map(dto -> {
                        Product product = productRepository.findByIdAndIsDeletedFalse(dto.productId())
                                .orElseThrow(() -> new ResourceNotFoundException("Product con id=" + dto.productId() +" non trovato"));
                        TypicalPackageItem item = new TypicalPackageItem();
                        item.setProduct(product);
                        item.setQuantity(dto.quantity());
                        item.setTypicalPackage(pkg);
                        return item;
                    })
                    .collect(Collectors.toSet());

            pkg.getTypicalPackageItems().clear();
            pkg.getTypicalPackageItems().addAll(items);
        }

        TypicalPackage saved = typicalPackageRepository.save(pkg);
        request.setTargetId(saved.getId());
    }
}

