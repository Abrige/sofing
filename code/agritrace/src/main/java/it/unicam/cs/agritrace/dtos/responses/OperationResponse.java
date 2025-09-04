package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record OperationResponse(
        Integer id,            // id della risorsa coinvolta
        String type,           // "PRODUCT" o "PACKAGE", utile per capire cosa è stato modificato
        String action,         // "CREATED", "UPDATED", "DELETED"
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Rome")
        Instant timestamp     // quando è stata effettuata l'operazione
) {}
