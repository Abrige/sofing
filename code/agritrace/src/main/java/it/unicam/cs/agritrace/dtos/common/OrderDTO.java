package it.unicam.cs.agritrace.dtos.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public record OrderDTO(

        @JsonProperty("order_id")
        @Schema(description = "Identificativo univoco dell'ordine", example = "1")
        int orderId,

        @Schema(description = "Acquirente che ha effettuato l'ordine")
        UserDTO buyer,

        @JsonProperty("total_amount")
        @Schema(description = "Importo totale dell'ordine in EUR", example = "14")
        BigDecimal totalAmount,

        @Schema(description = "Stato attuale dell'ordine", example = "SPEDITO")
        String status,

        @JsonProperty("ordered_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Europe/Rome")
        @Schema(description = "Data e ora in cui l'ordine è stato effettuato", example = "06/09/2025 14:35:20")
        Instant orderedAt,

        @JsonProperty("delivery_date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @Schema(description = "Data stimata di consegna", example = "10/09/2025")
        LocalDate deliveryDate,

        @JsonProperty("order_items")
        @Schema(description = "Lista degli articoli contenuti nell'ordine")
        Set<OrderItemDTO> orderItems) {
}
