
package it.unicam.cs.agritrace.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CertificationsResponse(
        Integer id,     //id della risorsa coinvolta
        String name,
        String description,
        @JsonProperty("issuing_body") String issuingBody

) {}


