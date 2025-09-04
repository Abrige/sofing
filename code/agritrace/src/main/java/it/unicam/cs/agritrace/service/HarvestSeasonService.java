package it.unicam.cs.agritrace.service;


import it.unicam.cs.agritrace.dtos.responses.HarvestSeasonResponse;
import it.unicam.cs.agritrace.model.HarvestSeason;
import it.unicam.cs.agritrace.repository.HarvestSeasonRepository;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.mapstruct.IterableMapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HarvestSeasonService {
    private final HarvestSeasonRepository harvestSeasonRepository;

    public HarvestSeasonService(HarvestSeasonRepository harvestSeasonRepository) {
        this.harvestSeasonRepository = harvestSeasonRepository;
    }

    public List<HarvestSeasonResponse> GetAllHarvestSeason(){
        List<HarvestSeason> AllHV = harvestSeasonRepository.findAll();

        List<HarvestSeasonResponse> AllHVResponse = AllHV.stream().map(
                Item -> new HarvestSeasonResponse(
                        Item.getId(),
                        Item.getName(),
                        Item.getMonthStart(),
                        Item.getMonthEnd(),
                        Item.getHemisphere(),
                        Item.getDescription()
                )).toList();

        return AllHVResponse;
    }

} 