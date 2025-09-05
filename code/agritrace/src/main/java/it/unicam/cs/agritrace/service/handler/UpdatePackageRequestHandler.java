package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
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
    private final PackageService packageService;
    private final ProductService productService;
    private final CompanyService companyService;

    public UpdatePackageRequestHandler(RequestTypeRepository requestTypeRepository,
                                       PayloadMapper payloadMapper,
                                       PackageService packageService,
                                       ProductService productService,
                                       CompanyService companyService) {
        this.requestType = requestTypeRepository.findByName("UPDATE_PACKAGE")
                .orElseThrow(() -> new ResourceNotFoundException("RequestType UPDATE_PACKAGE non trovato"));
        this.payloadMapper = payloadMapper;
        this.packageService = packageService;
        this.productService = productService;
        this.companyService = companyService;
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

        TypicalPackage pkg = packageService.getPackageById(payload.packageId());

        if (payload.name() != null) pkg.setName(payload.name());
        if (payload.description() != null) pkg.setDescription(payload.description());
        if (payload.price() != null) pkg.setPrice(payload.price());
        if (payload.producerId() != null) {
            pkg.setProducer(companyService.getCompanyById(payload.producerId()));
        }

        if (payload.items() != null && payload.items().size() >= 3) {
            Set<TypicalPackageItem> items = payload.items().stream()
                    .map(dto -> {
                        Product product = productService.findProductById(dto.productId());
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

        TypicalPackage saved = packageService.savePackage(pkg);
        request.setTargetId(saved.getId());
    }
}

