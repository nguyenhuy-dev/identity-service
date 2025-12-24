package org.fathand.identity_service.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.fathand.identity_service.validation.annotation.FieldMatch;
import org.springframework.beans.BeanUtils;

import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    String firstField;
    String secondField;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstField = constraintAnnotation.first();
        this.secondField = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object firstValue = Objects.requireNonNull(BeanUtils.getPropertyDescriptor(obj.getClass(), firstField)).getReadMethod().invoke(obj);

            Object secondValue = Objects.requireNonNull(BeanUtils.getPropertyDescriptor(obj.getClass(), secondField)).getReadMethod().invoke(obj);

            return Objects.equals(firstValue, secondValue);
        } catch (Exception e) {
            return false;
        }
    }
}
