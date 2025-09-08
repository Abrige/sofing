package it.unicam.cs.agritrace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.unicam.cs.agritrace.dtos.requests.AddEventRequest;
import it.unicam.cs.agritrace.dtos.responses.EventResponse;
import it.unicam.cs.agritrace.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            description = "Ritorna tutti gli eventi attivi e non ancora passati." +
                    "Può essere utilizzato solo da Produttore, Trasformatore, Distributore di tipicità"
    )
    @ApiResponse(responseCode = "200", description = "Eventi recuperati con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<List<EventResponse>> getAllEvents(){
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    /**
     * Restituisce un evento specifico dato l'ID, se attivo e non passato.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Recupera un evento per ID",
            description = "Restituisce i dettagli di un evento specifico, se attivo e non passato." +
                    "Può essere utilizzato solo da Produttore, Trasformatore, Distributore di tipicità"
    )
    @ApiResponse(responseCode = "200", description = "Evento recuperato con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Evento non trovato")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<EventResponse> getEventById(@PathVariable int id){
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    /**
     * Crea un nuovo evento (solo per l'animatore della filiera).
     */
    @PostMapping("/list")
    @Operation(
            summary = "Crea un nuovo evento",
            description = "Crea un evento nel sistema. Richiede ruolo Gestore della Piattaforma." +
                    "Può essere utilizzato solo da Animatore della filiera",
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
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @PreAuthorize("hasRole('ANIMATORE_DELLA_FILIERA')")
    public ResponseEntity<Void> createEvent(@RequestBody AddEventRequest event){
        //Utente fittizio con id 19 (nel db GESTORE DELLA PIATTAFORMA)
        eventService.createEvent(event, 19);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Partecipazione di un'azienda a un evento esistente.
     */
    @PostMapping("/partecipate/{id}")
    @Operation(
            summary = "Aggiunge un'azienda a un evento",
            description = "Associa un'azienda esistente a un evento specificato tramite ID." +
                    "Può essere utilizzato solo da Produttore, Trasformatore, Distributore di tipicità"
    )
    @ApiResponse(responseCode = "200", description = "Azienda aggiunta all'evento con successo")
    @ApiResponse(responseCode = "401", description = "Accesso non consentito")
    @ApiResponse(responseCode = "404", description = "Evento o azienda non trovato")
    @PreAuthorize("hasAnyRole('PRODUTTORE','TRASFORMATORE', 'DISTRIBUTORE_DI_TIPICITA')")
    public ResponseEntity<EventResponse> addCompanyToEvent(@PathVariable int id){
        eventService.addPartecipantToEvent(id);
        return ResponseEntity.ok(eventService.getEventById(id));
    }


}

