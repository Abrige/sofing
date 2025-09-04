package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Location;
import it.unicam.cs.agritrace.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getLocationById(int id){
        return locationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location with id=" + id + " not found"));
    }
}
