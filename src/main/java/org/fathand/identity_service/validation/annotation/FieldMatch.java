package org.fathand.identity_service.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.fathand.identity_service.validation.validator.FieldMatchValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {
    String message() default "Fields do not match";
    String first();
    String second();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
