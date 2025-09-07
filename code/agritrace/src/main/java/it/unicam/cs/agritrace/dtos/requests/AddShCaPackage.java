package it.unicam.cs.agritrace.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddShCaPackage(
        @JsonProperty("package_id")
        @Min(1)
        @NotNull
        Integer packageId,
        @Min(1)
        @NotNull
        Integer quantity
) {
}
