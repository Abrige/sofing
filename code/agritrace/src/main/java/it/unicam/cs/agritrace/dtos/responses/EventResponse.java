package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record EventResponse (@JsonProperty("event_id") int eventId,
                             String title,
                             String description,
                             @JsonProperty("start_date") LocalDate startDate,
                             @JsonProperty("end_date") LocalDate endDate,
                             String location){

}
