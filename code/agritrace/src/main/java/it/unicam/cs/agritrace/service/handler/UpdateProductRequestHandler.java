package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.ProductPayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.service.CompanyService;
import it.unicam.cs.agritrace.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateProductRequestHandler implements RequestHandler {

    private final RequestType requestType;
    private final PayloadMapper payloadMapper;
    private final ProductService productService;
    private final CompanyService companyService;

    public UpdateProductRequestHandler(RequestTypeRepository requestTypeRepository,
                                       PayloadMapper payloadMapper,
                                       ProductService productService,
                                       CompanyService companyService) {
        this.requestType = requestTypeRepository.findByName("UPDATE_PRODUCT") // ID = UPDATE_PRODUCT // TODO trovare un modo migliore per fare questa cosa
                .orElseThrow(() -> new ResourceNotFoundException("RequestType UPDATE_PRODUCT non trovato"));
        this.payloadMapper = payloadMapper;
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
        ProductPayload payload = payloadMapper.mapPayload(request, ProductPayload.class);

        if (payload.productId() == null) {
            throw new IllegalArgumentException("Per aggiornare un prodotto, l'ID deve essere specificato.");
        }

        Product product = productService.findProductById(payload.productId());

        if (payload.name() != null) product.setName(payload.name());
        if (payload.description() != null) product.setDescription(payload.description());
        if (payload.categoryId() != null) product.setCategory(productService.getProductCategoryById(payload.categoryId()));
        if (payload.cultivationMethodId() != null) product.setCultivationMethod(productService.getCultivationMethodById(payload.cultivationMethodId()));
        if (payload.harvestSeasonId() != null) product.setHarvestSeason(productService.getHarvestSeasonById(payload.harvestSeasonId()));
        if (payload.producerId() != null) product.setProducer(companyService.getCompanyById(payload.producerId()));

        Product saved = productService.saveProduct(product);
        request.setTargetId(saved.getId());
    }
}
