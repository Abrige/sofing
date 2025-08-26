package it.unicam.cs.agritrace.dtos.common;

import java.util.Map;

public record RawPayload(Map<String, Object> data) {

    public Object get(String key) {
        return data != null ? data.get(key) : null;
    }

    public RawPayload put(String key, Object value) {
        if(data != null) {
            data.put(key, value);  // attenzione: la mappa interna rimane mutabile
        }
        return this;
    }
}
