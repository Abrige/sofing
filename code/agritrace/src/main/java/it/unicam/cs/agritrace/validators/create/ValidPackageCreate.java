package it.unicam.cs.agritrace.validators.create;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PackageCreateValidator.class)
@Documented
public @interface ValidPackageCreate {
    String message() default "Payload non valido per la creazione del package";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
