package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CertificationRequest (String name,
                                    String description,
                                    @JsonProperty("issuing_body") String issuingBody) {
}
