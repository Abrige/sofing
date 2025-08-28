package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyDTO(
        String name,
        @JsonProperty("fiscal_code")
        String fiscalCode) {
}
