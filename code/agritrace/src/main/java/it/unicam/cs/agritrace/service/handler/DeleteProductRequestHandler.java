package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.payloads.DeletePayload;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.service.ProductService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeleteProductRequestHandler implements RequestHandler {

    private final RequestType requestType;
    private final PayloadMapper payloadMapper;
    private final ProductService productService;

    public DeleteProductRequestHandler(RequestTypeRepository requestTypeRepository,
                                       PayloadMapper payloadMapper,
                                       ProductService productService) {
        this.requestType = requestTypeRepository.findByName("DELETE_PRODUCT")
                .orElseThrow(() -> new ResourceNotFoundException("RequestType DELETE_PRODUCT non trovato"));
        this.payloadMapper = payloadMapper;
        this.productService = productService;
    }

    @Override
    public RequestType getSupportedRequestType() {
        return requestType;
    }


    @Transactional
    @Override
    public void handle(Request request) {
        DeletePayload payload = payloadMapper.mapPayload(request, DeletePayload.class);

        Product product = productService.findProductById(payload.targetId());
        product.setIsDeleted(true); // soft delete
        Product saved = productService.saveProduct(product);

        request.setTargetId(saved.getId());
    }
}
