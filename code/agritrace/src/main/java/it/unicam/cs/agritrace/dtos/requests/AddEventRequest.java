package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record AddEventRequest (@JsonProperty("organizer_id") int organizerId,
                               String title,
                               String description,
                               @JsonProperty("event_type_id") int eventTypeId,
                               @JsonProperty("start_date") LocalDate startDate,
                               @JsonProperty("end_date") LocalDate endDate,
                               @JsonProperty("location_id") int location

){
}
