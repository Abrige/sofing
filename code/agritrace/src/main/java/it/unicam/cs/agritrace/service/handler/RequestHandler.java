package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;

public interface RequestHandler {

    // Indica che tipo di request gestisce (l'entità, non una stringa a caso)
    RequestType getSupportedRequestType();

    // Esegue la logica con il payload già mappato
    void handle(Request request);
}
