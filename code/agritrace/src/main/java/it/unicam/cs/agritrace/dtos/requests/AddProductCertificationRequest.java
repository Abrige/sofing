package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddProductCertificationRequest(

        @NotNull
        @Min(1)
        @JsonProperty("certification_id")
        Integer certificationId,

        @NotNull
        @Min(1)
        @JsonProperty("product_id")
        Integer productId,

        @NotEmpty
        @JsonProperty("certificate_number")
        String certificateNumber,

        @NotNull
        @JsonProperty("issue_date")
        LocalDate issueDate,

        @NotNull
        @JsonProperty("expiry_date")
        LocalDate expiryDate
) {
}
