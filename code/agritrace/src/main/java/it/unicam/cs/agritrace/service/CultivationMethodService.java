package it.unicam.cs.agritrace.service;


import it.unicam.cs.agritrace.dtos.responses.CultivationMethodResponse;
import it.unicam.cs.agritrace.model.CultivationMethod;
import it.unicam.cs.agritrace.repository.CultivationMethodRepository;
import it.unicam.cs.agritrace.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CultivationMethodService {
    private  final CultivationMethodRepository cultivationMethodRepository;

    public CultivationMethodService(CultivationMethodRepository cultivationMethodRepository, ProductCategoryRepository productCategoryRepository) {
        this.cultivationMethodRepository = cultivationMethodRepository;
    }

    public List<CultivationMethodResponse> getCultivationMethodAll(){
        List<CultivationMethod> cultivationMethods = cultivationMethodRepository.findAllByIsActiveTrue();

        List<CultivationMethodResponse> cultivationMethodResponse = cultivationMethods.stream().map(
        cMR -> new CultivationMethodResponse(
                cMR.getId(),
                cMR.getName(),
                cMR.getDescription()
        )
                ).toList();

        return cultivationMethodResponse;
    }
}
