package it.unicam.cs.agritrace.dto;

public record ProductRequestDto(String name,
                                String description,
                                Integer categoryId,
                                Integer cultivationMethodId,
                                Integer harvestSeasonId,
                                Integer producerId)
{

}
