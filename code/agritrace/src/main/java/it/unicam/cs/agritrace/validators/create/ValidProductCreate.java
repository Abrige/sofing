package it.unicam.cs.agritrace.validators.create;

import it.unicam.cs.agritrace.validators.create.ProductCreateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProductCreateValidator.class)
@Documented
public @interface ValidProductCreate {
    String message() default "Payload non valido per la creazione del prodotto";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
