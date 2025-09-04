package it.unicam.cs.agritrace.validators.update;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PackageUpdateValidator.class)
@Documented
public @interface ValidPackageUpdate {
    String message() default "Payload non valido per l'aggiornamento del package";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
