package it.unicam.cs.agritrace.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.model.Request;
import org.springframework.stereotype.Component;

@Component
public class PayloadMapper {

    private final ObjectMapper mapper;

    public PayloadMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    // Da payload di richiesta a dto corretto
    public <T> T mapPayload(Request request, Class<T> dtoClass) {
        try {
            return mapper.readValue(request.getPayload(), dtoClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore nel parsing del payload: " + e.getMessage(), e);
        }
    }

    // Da dto a payload JSON
    public String toJson(Object dto) {
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Errore durante la serializzazione in JSON: " + e.getMessage(), e);
        }
    }
}
