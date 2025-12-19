package org.fathand.identity_service.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.fathand.identity_service.validation.annotation.ValidDob;

import java.time.LocalDate;
import java.time.Period;

public class DobValidator implements ConstraintValidator<ValidDob, LocalDate> {
    private int minAge;

    @Override
    public void initialize(ValidDob constraintAnnotation) {
        this.minAge = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext constraintValidatorContext) {
        if (dob == null) return true;

        LocalDate today = LocalDate.now();
        Period age = Period.between(dob, today);

        return age.getYears() >= minAge;
    }
}
