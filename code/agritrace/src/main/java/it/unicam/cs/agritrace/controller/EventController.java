package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.requests.AddEventRequest;
import it.unicam.cs.agritrace.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/events")
@Tag(name = "Eventi", description = "Gestione degli eventi nel sistema") // Gruppo in Swagger UI
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Restituisce tutti gli eventi attivi e non ancora passati.
     */
    @GetMapping("/list")
    @Operation(
            summary = "Lista eventi attivi",
            description = "Ritorna tutti gli eventi attivi e non ancora passati."
    )
    @ApiResponse(responseCode = "200", description = "Eventi recuperati con successo")
    public ResponseEntity<?> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * Restituisce un evento specifico dato l'ID, se attivo e non passato.
     */
    @GetMapping("/list/{id}")
    @Operation(
            summary = "Recupera un evento per ID",
            description = "Restituisce i dettagli di un evento specifico, se attivo e non passato."
    )
    @ApiResponse(responseCode = "200", description = "Evento recuperato con successo")
    @ApiResponse(responseCode = "404", description = "Evento non trovato")
    public ResponseEntity<?> getEventById(@PathVariable int id){
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    /**
     * Crea un nuovo evento (solo per il Gestore della Piattaforma).
     */
    @PostMapping("/list")
    @Operation(
            summary = "Crea un nuovo evento",
            description = "Crea un evento nel sistema. Richiede ruolo Gestore della Piattaforma.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati del nuovo evento",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Evento esempio",
                                            summary = "Crea un evento fittizio",
                                            value = """
                        {
                          "organizer_id" : 1,
                          "title" : "Fiera della filera",
                          "description" : "Mostra i prodotti locali in vendita",
                          "start_date" : "2025-11-23",
                          "end_date" : "2025-11-28",
                          "location_id" : 1
                        }
                        """
                                    )
                            }
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Evento creato con successo")
    public ResponseEntity<?> createEvent(@RequestBody AddEventRequest event){
        //Utente fittizio con id 19 (nel db GESTORE DELLA PIATTAFORMA)
        eventService.createEvent(event, 19);
        return ResponseEntity.ok(event);
    }

    /**
     * Aggiunge un'azienda a un evento esistente.
     */
    @PostMapping("/list/{eventId}/company/{companyId}")
    @Operation(
            summary = "Aggiunge un'azienda a un evento",
            description = "Associa un'azienda esistente a un evento specificato tramite ID."
    )
    @ApiResponse(responseCode = "200", description = "Azienda aggiunta all'evento con successo")
    @ApiResponse(responseCode = "404", description = "Evento o azienda non trovato")
    public ResponseEntity<?> addCompanyToEvent(@PathVariable int eventId, @PathVariable int companyId){
        eventService.addCompanyToEvent(eventId, companyId);
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }


}


//Quale DTO usa questo controller?