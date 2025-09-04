package it.unicam.cs.agritrace.repository;

import it.unicam.cs.agritrace.model.Certification;
import it.unicam.cs.agritrace.model.HarvestSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HarvestSeasonRepository extends JpaRepository<HarvestSeason, Integer> {

}