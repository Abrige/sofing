package it.unicam.cs.agritrace.validators.create;

import it.unicam.cs.agritrace.dtos.payloads.PackagePayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PackageCreateValidator implements ConstraintValidator<ValidPackageCreate, PackagePayload> {

    @Override
    public boolean isValid(PackagePayload payload, ConstraintValidatorContext context) {
        if (payload == null) return false;
        context.disableDefaultConstraintViolation();

        boolean valid = true;

        // packageId deve essere null
        if (payload.packageId() != null) {
            context.buildConstraintViolationWithTemplate("packageId deve essere null in creazione")
                    .addPropertyNode("packageId").addConstraintViolation();
            valid = false;
        }

        // tutti gli altri campi devono essere presenti
        if (payload.name() == null) {
            context.buildConstraintViolationWithTemplate("name è obbligatorio")
                    .addPropertyNode("name").addConstraintViolation();
            valid = false;
        }
        if (payload.description() == null) {
            context.buildConstraintViolationWithTemplate("description è obbligatorio")
                    .addPropertyNode("description").addConstraintViolation();
            valid = false;
        }
        if (payload.price() == null) {
            context.buildConstraintViolationWithTemplate("price è obbligatorio")
                    .addPropertyNode("price").addConstraintViolation();
            valid = false;
        }
        if (payload.producerId() == null) {
            context.buildConstraintViolationWithTemplate("producerId è obbligatorio")
                    .addPropertyNode("producerId").addConstraintViolation();
            valid = false;
        }
        if (payload.items() == null || payload.items().isEmpty()) {
            context.buildConstraintViolationWithTemplate("items è obbligatorio e non può essere vuoto")
                    .addPropertyNode("items").addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
