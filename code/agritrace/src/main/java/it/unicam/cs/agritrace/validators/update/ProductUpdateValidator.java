package it.unicam.cs.agritrace.validators.update;

import it.unicam.cs.agritrace.dtos.payloads.ProductPayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductUpdateValidator implements ConstraintValidator<ValidProductUpdate, ProductPayload> {

    @Override
    public boolean isValid(ProductPayload payload, ConstraintValidatorContext context) {
        if (payload == null) return false;
        context.disableDefaultConstraintViolation();

        boolean valid = true;

        // productId deve essere valorizzato
        if (payload.productId() == null) {
            context.buildConstraintViolationWithTemplate("productId è obbligatorio per l'update")
                    .addPropertyNode("productId").addConstraintViolation();
            valid = false;
        }

        // almeno un altro campo deve essere valorizzato
        boolean anyOtherPresent =
                payload.name() != null ||
                        payload.description() != null ||
                        payload.categoryId() != null ||
                        payload.cultivationMethodId() != null ||
                        payload.harvestSeasonId() != null ||
                        payload.producerId() != null;

        if (!anyOtherPresent) {
            context.buildConstraintViolationWithTemplate("Almeno un campo oltre productId deve essere valorizzato")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
