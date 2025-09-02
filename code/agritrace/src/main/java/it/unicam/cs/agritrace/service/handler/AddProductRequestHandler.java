package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.AddProductPayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.service.CompanyService;
import it.unicam.cs.agritrace.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class AddProductRequestHandler implements RequestHandler {
    private final RequestType requestType;
    private final PayloadMapper payloadMapper;
    private final ProductService productService;
    private final CompanyService companyService;


    public AddProductRequestHandler(RequestTypeRepository requestTypeRepository,
                                    PayloadMapper payloadMapper,
                                    ProductService productService,
                                    CompanyService companyService) {
        // ADD PRODUCT
        this.requestType = requestTypeRepository.findById(3).orElseThrow(
                () -> new ResourceNotFoundException("RequestType ADD_PRODUCT non trovato sul db"));
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
        // 1. Converto JSON in DTO
        AddProductPayload payload = payloadMapper.mapPayload(request, AddProductPayload.class);
        // 2. Creo il prodotto
        Product product = new Product();
        // 3. Popolo il prodotto dal DTO
        product.setName(payload.name());
        product.setDescription(payload.description());
        ProductCategory category = productService.getProductCategoryById(payload.categoryId());
        product.setCategory(category);
        CultivationMethod cultivationMethod = productService.getCultivationMethodById(payload.cultivationMethodId());
        product.setCultivationMethod(cultivationMethod);
        HarvestSeason harvestSeason = productService.getHarvestSeasonById(payload.harvestSeasonId());
        product.setHarvestSeason(harvestSeason);
        Company company = companyService.getCompanyById(payload.producerId());
        product.setProducer(company);
        product.setIsDeleted(false);

        // 4. Salvo il prodotto
        Product saved = productService.saveProduct(product);

        // 5. Aggiorno la request col targetId
        request.setTargetId(saved.getId());

    }
}
