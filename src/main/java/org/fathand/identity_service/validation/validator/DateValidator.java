package org.fathand.identity_service.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fathand.identity_service.validation.annotation.ValidDate;

import java.time.LocalDate;
import java.time.Period;

public class DateValidator implements ConstraintValidator<ValidDate, LocalDate> {
    private int minAge;

    @Override
    public void initialize(ValidDate constraintAnnotation) {
        this.minAge = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) return true;

        LocalDate today = LocalDate.now();
        Period age = Period.between(date, today);

        return age.getYears() >= minAge;
    }
}
