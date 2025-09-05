package it.unicam.cs.agritrace.dtos.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public record CertificationPayload (
        @JsonProperty("certification_id")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        int certificationId,
        @NotEmpty
        String name,
        String description,
        @JsonProperty("issuing_body")
        @NotEmpty
        String issuingBody
) {}
