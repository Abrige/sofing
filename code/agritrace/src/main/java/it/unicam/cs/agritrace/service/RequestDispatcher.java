package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.service.handler.RequestHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestDispatcher {

    private final Map<String, RequestHandler<?>> handlers;

    public RequestDispatcher(List<RequestHandler<?>> handlerList) {
        handlers = handlerList.stream()
                .collect(Collectors.toMap(RequestHandler::getRequestTypeName, Function.identity()));
    }

    public void dispatch(Request request) {
        RequestHandler<?> handler = handlers.get(request.getRequestType().getName());
        if (handler == null) {
            throw new IllegalArgumentException("Request type non gestito: " + request.getRequestType().getName());
        }

        // Cast sicuro e chiamata dell'handler
        ((RequestHandler<Request>) handler).handle(request);
    }
}
