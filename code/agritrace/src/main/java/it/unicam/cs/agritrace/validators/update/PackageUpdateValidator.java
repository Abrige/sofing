package it.unicam.cs.agritrace.validators.update;

import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PackageUpdateValidator implements ConstraintValidator<ValidPackageUpdate, PackagePayload> {

    @Override
    public boolean isValid(PackagePayload payload, ConstraintValidatorContext context) {
        if (payload == null) return false;
        context.disableDefaultConstraintViolation();

        boolean valid = true;

        // packageId deve essere presente
        if (payload.packageId() == null) {
            context.buildConstraintViolationWithTemplate("packageId è obbligatorio per l'update")
                    .addPropertyNode("packageId").addConstraintViolation();
            valid = false;
        }

        // almeno un campo diverso da packageId deve essere presente
        boolean anyOtherPresent =
                payload.name() != null ||
                        payload.description() != null ||
                        payload.price() != null ||
                        payload.producerId() != null ||
                        (payload.items() != null && !payload.items().isEmpty());

        if (!anyOtherPresent) {
            context.buildConstraintViolationWithTemplate("Almeno un campo oltre packageId deve essere valorizzato")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
