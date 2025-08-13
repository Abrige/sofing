package it.unicam.cs.agritrace.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter globale per tutti i campi Boolean <-> TINYINT(1)
 * Applica automaticamente a tutte le entità grazie a autoApply = true.
 */
@Converter(autoApply = true)
public class BooleanTinyIntConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return 0; // Se vuoi salvare null come 0
        }
        return attribute ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        return dbData != null && dbData == 1;
    }
}