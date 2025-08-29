package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.model.Request;

public interface RequestHandler<T> {

    // Esegue la logica del tipo di request
    void handle(Request request);

    // Ritorna il nome della request che questo handler gestisce
    String getRequestTypeName();
}
