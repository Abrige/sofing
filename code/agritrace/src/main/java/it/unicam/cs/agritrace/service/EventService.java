package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.requests.AddEventRequest;
import it.unicam.cs.agritrace.dtos.responses.EventResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final EventPartecipantRepository eventPartecipantRepository;
    private final StatusRepository statusRepository;

    public EventService(EventRepository eventRepository,
                        EventTypeRepository eventTypeRepository, LocationRepository locationRepository, UserRepository userRepository, CompanyRepository companyRepository,
                        EventPartecipantRepository eventPartecipantRepository, StatusRepository statusRepository) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.eventPartecipantRepository = eventPartecipantRepository;
        this.statusRepository = statusRepository;
    }

    public List<EventResponse> getAllEvents(){
        return eventRepository.findAllByIsActiveTrue().stream().filter(item -> item.getEndDate().isAfter(LocalDate.now())).map(
                event -> new EventResponse(
                        event.getId(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getLocation().getName()
                )
        ).toList();
    }

    public EventResponse getEventById(int id) {
        Event event = eventRepository.findByIdAndIsActiveTrue(id).orElseThrow(() -> new ResourceNotFoundException("Evento non trovato: " + id));
        if (event.getEndDate().isBefore(LocalDate.now()))
            throw new ResourceNotFoundException("Evento passato: " + id);
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartDate(),
                event.getEndDate(),
                event.getLocation().getName()
        );
    }

    public void createEvent(AddEventRequest eventRequest, int userId) {
        Event event = new Event();
        EventType eventType = eventTypeRepository.findById(eventRequest.eventTypeId()).orElseThrow(() -> new ResourceNotFoundException(" Tipo di evento non trovato") );
        Location location = locationRepository.findById(eventRequest.location()).orElseThrow(()-> new ResourceNotFoundException("Luogo non trovato"));
        User user = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
        event.setEventType(eventType);
        event.setDescription(eventRequest.description());
        event.setStartDate(eventRequest.startDate());
        event.setEndDate(eventRequest.endDate());
        event.setLocation(location);
        event.setTitle(eventRequest.title());
        event.setOrganizer(user);
        event.setIsActive(true);
        eventRepository.save(event);
    }

    //public void deleteEventById(int id) {}

    public void addCompanyToEvent(int eventId, int companyId) {
        Event event = eventRepository.findByIdAndIsActiveTrue(eventId).orElseThrow(() -> new ResourceNotFoundException("Evento non trovato"));
        if(event.getEndDate().isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("Evento già concluso");
        }
        if(event.getStartDate().isBefore(LocalDate.now())){
            throw new ResourceNotFoundException("Evento già iniziato");
        }
        Company company = companyRepository.findById(companyId).orElseThrow(() ->new ResourceNotFoundException("Azienda non trovata"));
        //LO STATUS VA SETTATO, PERCHE' PRESENTE NEL DB. QUI LO SETTO A 1 (NEW).
        Status status = statusRepository.findById(1).orElseThrow(() ->new ResourceNotFoundException("Status non trovato"));
        //Creo la partecipazione all'evento
        EventPartecipant partecipant = new EventPartecipant();
        partecipant.setEvent(event);
        partecipant.setCreatedAt(Instant.now());
        partecipant.setRespondedAt(Instant.now());
        partecipant.setCompany(company);
        partecipant.setInviter(event.getOrganizer());
        partecipant.setStatus(status);
        eventPartecipantRepository.save(partecipant);
        event.getEventPartecipants().add(partecipant);
        eventRepository.save(event);
    }
}
