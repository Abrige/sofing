package it.unicam.cs.agritrace.dtos.common;

import java.time.LocalDate;

public record RawMaterialDTO(int input_product_id, int output_product_id, int company_id, String step_type, String description, LocalDate stepDate) { }
