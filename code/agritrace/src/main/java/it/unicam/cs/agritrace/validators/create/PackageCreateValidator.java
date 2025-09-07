package it.unicam.cs.agritrace.validators.create;

import it.unicam.cs.agritrace.dtos.payloads.PackageCreateUpdatePayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PackageCreateValidator implements ConstraintValidator<ValidPackageCreate, PackageCreateUpdatePayload> {

    @Override
    public boolean isValid(PackageCreateUpdatePayload payload, ConstraintValidatorContext context) {
        if (payload == null) return false;
        context.disableDefaultConstraintViolation();

        boolean valid = true;

        // packageId deve essere null
        if (payload.packageId() != null) {
            context.buildConstraintViolationWithTemplate("packageId deve essere null in creazione")
                    .addPropertyNode("packageId").addConstraintViolation();
            valid = false;
        }

        // name obbligatorio
        if (payload.name() == null) {
            context.buildConstraintViolationWithTemplate("name è obbligatorio")
                    .addPropertyNode("name").addConstraintViolation();
            valid = false;
        }

        // description obbligatorio
        if (payload.description() == null) {
            context.buildConstraintViolationWithTemplate("description è obbligatorio")
                    .addPropertyNode("description").addConstraintViolation();
            valid = false;
        }

        // price obbligatorio
        if (payload.price() == null) {
            context.buildConstraintViolationWithTemplate("price è obbligatorio")
                    .addPropertyNode("price").addConstraintViolation();
            valid = false;
        }

        // items obbligatorio e con almeno 3 prodotti
        if (payload.items() == null || payload.items().isEmpty()) {
            context.buildConstraintViolationWithTemplate("items è obbligatorio e non può essere vuoto")
                    .addPropertyNode("items").addConstraintViolation();
            valid = false;
        } else if (payload.items().size() < 3) {
            context.buildConstraintViolationWithTemplate("Il pacchetto deve contenere almeno 3 prodotti")
                    .addPropertyNode("items").addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
