package it.unicam.cs.agritrace.service.factory;

import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.service.handler.RequestHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestHandlerFactory {

    private final Map<Integer, RequestHandler> handlers = new HashMap<>();

    public RequestHandlerFactory(List<RequestHandler> handlerList) {
        // indicizza per id del RequestType
        for (RequestHandler handler : handlerList) {
            handlers.put(handler.getSupportedRequestType().getId(), handler);
        }
    }

    public RequestHandler getHandler(RequestType requestType) {
        RequestHandler handler = handlers.get(requestType.getId());
        if (handler == null) {
            throw new IllegalArgumentException("Nessun handler registrato per requestType: " + requestType.getName());
        }
        return handler;
    }
}
