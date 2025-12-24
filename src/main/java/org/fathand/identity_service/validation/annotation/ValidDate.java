package org.fathand.identity_service.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.fathand.identity_service.validation.validator.DateValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface ValidDate {
    String message() default "Invalid date input";

    int min() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
