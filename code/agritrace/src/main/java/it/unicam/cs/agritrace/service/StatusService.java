package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.exceptions.OrderStatusInvalidException;
import it.unicam.cs.agritrace.model.Status;
import it.unicam.cs.agritrace.repository.StatusRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status getStatusByName(String name) {
        return statusRepository.findByName(name).orElseThrow(() -> new OrderStatusInvalidException(name));
    }
}
