package it.unicam.cs.agritrace.exceptions;

import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class InvalidPayloadException extends RuntimeException {
    private final Errors errors;

    public InvalidPayloadException(Errors errors) {
        super("Payload non valido");
        this.errors = errors;
    }

}