package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.ProductDTO;
import it.unicam.cs.agritrace.dtos.payloads.AddRawMaterialPayload;
import it.unicam.cs.agritrace.mappers.ProductMapper;
import it.unicam.cs.agritrace.mappers.ProductMapperImpl;
import it.unicam.cs.agritrace.model.Product;
import it.unicam.cs.agritrace.model.ProductionStep;
import it.unicam.cs.agritrace.repository.ProductionStepRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplyChainService {
    private final ProductService productService;
    private final ProductionStepRepository productionStepRepository;
    private final ProductMapper productMapper;

    public SupplyChainService(ProductService productService, ProductionStepRepository productionStepRepository, ProductMapper productMapper) {
        this.productService = productService;
        this.productionStepRepository = productionStepRepository;
        this.productMapper = productMapper;
    }

    //Crea un oggetto Production Step
    // input : prodotto scelto (rawMaterial)
    // output : prodotto trasformato creato dal trasformatore già esistente
    @Transactional
    public void addRawMaterials(int productId, List<AddRawMaterialPayload> payloads) {
        Product product = productService.getProductObjById(productId);
        for(AddRawMaterialPayload p : payloads) {
            Product rawMaterial = productService.getProductObjById(p.productId());
            ProductionStep productionStep = new ProductionStep();
            productionStep.setInputProduct(rawMaterial);
            productionStep.setOutputProduct(product);
            productionStep.setDescription(p.description());
            productionStep.setStepType("trasformato");
            productionStep.setStepDate(LocalDate.now());
            // TODO attributo location e company

            productionStepRepository.save(productionStep);
        }
    }
    //Rimuove la relazione tra raw material e prodotto trasformato
    @Transactional
    public void removeRawMaterial (int productId, int rawMaterialId) {
        List<ProductionStep> productionStep = productionStepRepository
                .findByInputProductIdAndOutputProductId(rawMaterialId, productId);
        productionStepRepository.deleteAll(productionStep);
    }

    @Transactional
    public List<ProductDTO> getRawMaterialsByOutputProduct(int productId) {
        return productMapper.toDtoList(productionStepRepository.findByOutputProductId(productId));
    }
}
