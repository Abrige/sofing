package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.AddPackagePayload;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.service.PackageService;
import it.unicam.cs.agritrace.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddPackageRequestHandler implements RequestHandler {

    private final PackageService packageService;
    private final PayloadMapper payloadMapper;
    private final RequestType supportedRequestType;
    private final ProductService productService;

    public AddPackageRequestHandler(PackageService packageService,
                                    PayloadMapper payloadMapper,
                                    RequestTypeRepository requestTypeRepository,
                                    ProductService productService) {
        this.packageService = packageService;
        this.payloadMapper = payloadMapper;
        this.supportedRequestType = requestTypeRepository.findById(2) // ID di ADD_PACKAGE
                .orElseThrow(() -> new IllegalStateException("RequestType ADD_PACKAGE non trovato"));
        this.productService = productService;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return supportedRequestType;
    }

    @Override
    @Transactional
    public void handle(Request request) {
        // 1. Converto JSON in DTO
        AddPackagePayload payload = payloadMapper.mapPayload(request, AddPackagePayload.class);

        // 2. Creo il pacchetto
        TypicalPackage pkg = new TypicalPackage();
        pkg.setName(payload.name());
        pkg.setDescription(payload.description());
        pkg.setPrice(payload.price());

        // produttore
        Company producer = packageService.findCompanyById(payload.producerId());
        pkg.setProducer(producer);
        pkg.setIsActive(true);

        // 3. Creo gli item
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

        pkg.setTypicalPackageItems(items);

        // 4. Salvo pacchetto e item
        TypicalPackage saved = packageService.savePackage(pkg);

        // 5. Aggiorno la request col targetId
        request.setTargetId(saved.getId());
    }
}
