package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.ProductRepository;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.repository.TypicalPackageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddPackageRequestHandler implements RequestHandler {

    private final PayloadMapper payloadMapper;
    private final RequestType supportedRequestType;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final TypicalPackageRepository typicalPackageRepository;

    public AddPackageRequestHandler(
            PayloadMapper payloadMapper,
            RequestTypeRepository requestTypeRepository,
            ProductRepository productRepository,
            CompanyRepository companyRepository,
            TypicalPackageRepository typicalPackageRepository) {

        this.payloadMapper = payloadMapper;
        this.supportedRequestType = requestTypeRepository.findByName("ADD_PACKAGE")
                .orElseThrow(() -> new ResourceNotFoundException("RequestType ADD_PACKAGE non trovato"));
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.typicalPackageRepository = typicalPackageRepository;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return supportedRequestType;
    }

    @Override
    @Transactional
    public void handle(Request request) {
        PackagePayload payload = payloadMapper.mapPayload(request, PackagePayload.class);

        TypicalPackage pkg = new TypicalPackage();
        pkg.setName(payload.name());
        pkg.setDescription(payload.description());
        pkg.setPrice(payload.price());
        pkg.setIsActive(true);

        // produttore
        Company producer = companyRepository.findById(payload.producerId())
                .orElseThrow(() -> new ResourceNotFoundException("Company con id=" + payload.producerId() + " non trovato"));
        pkg.setProducer(producer);

        // items
        Set<TypicalPackageItem> items = payload.items().stream()
                .map(dto -> {
                    Product product = productRepository.findById(dto.productId())
                            .orElseThrow(() -> new ResourceNotFoundException("Prodotto con id=" + dto.productId() + " non trovato"));
                    TypicalPackageItem item = new TypicalPackageItem();
                    item.setProduct(product);
                    item.setQuantity(dto.quantity());
                    item.setTypicalPackage(pkg);
                    return item;
                })
                .collect(Collectors.toSet());

        pkg.setTypicalPackageItems(items);

        TypicalPackage saved = typicalPackageRepository.save(pkg);
        request.setTargetId(saved.getId());
    }
}
