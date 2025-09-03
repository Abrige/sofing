package it.unicam.cs.agritrace.validators.update;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductUpdateValidator.class)
@Documented
public @interface ValidProductUpdate {
    String message() default "Payload non valido per l'aggiornamento del prodotto";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
