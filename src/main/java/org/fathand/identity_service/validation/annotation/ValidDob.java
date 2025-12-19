package org.fathand.identity_service.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.fathand.identity_service.validation.validator.DobValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DobValidator.class)
@Documented
public @interface ValidDob {
    String message() default "Invalid date of birth";

    int min() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
