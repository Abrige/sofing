package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;


/*
        JSON DI ESEMPIO:
        {
            "name":"Company di prova",
            "fiscal_code": "fiscalcodediprova",
            "location_id": 1,
            "description": "descrizione di prova",
            "company_type_id": 1
        }
*/
@JsonInclude(JsonInclude.Include.NON_NULL) // i campi nulli non li serializza
public record AddCompanyRequest(

        @NotEmpty
        String name,

        @JsonProperty("fiscal_code")
        @NotEmpty
        String fiscalCode,

        @NotNull
        @Min(1)
        @JsonProperty("location_id")
        Integer locationId,

        String description,

        @JsonProperty("website_url")
        String websiteUrl,

        @NotNull
        @Range(min = 1, max = 3)
        @JsonProperty("company_type_id")
        Integer companyTypeId
) {
}
