package it.unicam.cs.agritrace.validators.update;

import it.unicam.cs.agritrace.dtos.payloads.PackageCreateUpdatePayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PackageUpdateValidator implements ConstraintValidator<ValidPackageUpdate, PackageCreateUpdatePayload> {

    @Override
    public boolean isValid(PackageCreateUpdatePayload payload, ConstraintValidatorContext context) {
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
                        (payload.items() != null && !payload.items().isEmpty());

        if (!anyOtherPresent) {
            context.buildConstraintViolationWithTemplate("Almeno un campo oltre packageId deve essere valorizzato")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
