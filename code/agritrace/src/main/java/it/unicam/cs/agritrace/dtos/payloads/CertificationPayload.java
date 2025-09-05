package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CertificationPayload (@JsonProperty("certification_id")
                                    @JsonInclude(JsonInclude.Include.NON_NULL)
                                    int certificationId,
                                    String name,
                                    String description,
                                    @JsonProperty("issuing_body") String issuingBody
                                    ) {
}
