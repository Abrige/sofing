package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.HarvestSeason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarvestSeasonRepository extends JpaRepository<HarvestSeason, Integer> {}