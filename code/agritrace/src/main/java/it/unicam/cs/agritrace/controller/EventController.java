package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.AddEventRequest;
import it.unicam.cs.agritrace.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //Ritorna tutti gli eventi che sono attivi e che non sono già passati
    @GetMapping("/list")
    public ResponseEntity<?> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    //Ritorna l'evento selezionato, se attivo e non già passato
    @GetMapping("/list/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id){
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    //Crea evento (lo fa il Gestore della Piattaforma)
    @PostMapping("/list")
    public ResponseEntity<?> createEvent(@RequestBody AddEventRequest event){
        //Utente fittizio con id 19 (nel db GESTORE DELLA PIATTAFORMA)
        eventService.createEvent(event, 19);
        return ResponseEntity.ok(event);
    }

    //Aggiunge un azienda a un evento
    @PostMapping("/list/{eventId}/company/{companyId}")
    public ResponseEntity<?> addCompanyToEvent(@PathVariable int eventId, @PathVariable int companyId){
        eventService.addCompanyToEvent(eventId, companyId);
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }


}
