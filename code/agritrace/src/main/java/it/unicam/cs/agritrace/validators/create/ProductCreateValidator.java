package it.unicam.cs.agritrace.validators.create;

import it.unicam.cs.agritrace.dtos.payloads.ProductPayload;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductCreateValidator implements ConstraintValidator<ValidProductCreate, ProductPayload> {

    @Override
    public boolean isValid(ProductPayload payload, ConstraintValidatorContext context) {
        if (payload == null) return false;
        context.disableDefaultConstraintViolation();

        boolean valid = true;

        // productId deve essere null
        if (payload.productId() != null) {
            context.buildConstraintViolationWithTemplate("productId deve essere null")
                    .addPropertyNode("productId").addConstraintViolation();
            valid = false;
        }

        // tutti gli altri devono essere != null
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
        if (payload.categoryId() == null) {
            context.buildConstraintViolationWithTemplate("categoryId è obbligatorio")
                    .addPropertyNode("categoryId").addConstraintViolation();
            valid = false;
        }
        if (payload.cultivationMethodId() == null) {
            context.buildConstraintViolationWithTemplate("cultivationMethodId è obbligatorio")
                    .addPropertyNode("cultivationMethodId").addConstraintViolation();
            valid = false;
        }
        if (payload.harvestSeasonId() == null) {
            context.buildConstraintViolationWithTemplate("harvestSeasonId è obbligatorio")
                    .addPropertyNode("harvestSeasonId").addConstraintViolation();
            valid = false;
        }
        if (payload.producerId() == null) {
            context.buildConstraintViolationWithTemplate("producerId è obbligatorio")
                    .addPropertyNode("producerId").addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
