package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.RawPayload;
import it.unicam.cs.agritrace.model.ProductCategory;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.CultivationMethodRepository;
import it.unicam.cs.agritrace.repository.HarvestSeasonRepository;
import it.unicam.cs.agritrace.repository.ProductCategoryRepository;
import it.unicam.cs.agritrace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayloadEnricher {

    private final ProductCategoryRepository productCategoryRepository;
    private final CultivationMethodRepository cultivationMethodRepository;
    private final HarvestSeasonRepository harvestSeasonRepository;
    private final UserRepository userRepository;

    public Map<String,Object> enrich(RawPayload rawPayload, String requestType) {
        Map<String,Object> data = rawPayload.data();
        if(data == null) return Map.of();

        // esempio generico
        if(data.containsKey("category_id")) {
            Integer categoryId = (Integer) data.get("category_id");
            if(categoryId != null) {
                data.put("categoryName", productCategoryRepository.findById(categoryId)
                        .map(ProductCategory::getName).orElse(null));
            }
        }

        if(data.containsKey("seller_id")) {
            Integer sellerId = (Integer) data.get("seller_id");
            if(sellerId != null) {
                data.put("sellerName", userRepository.findById(sellerId)
                        .map(User::getUsername).orElse(null));
            }
        }

        return data;
    }
}
