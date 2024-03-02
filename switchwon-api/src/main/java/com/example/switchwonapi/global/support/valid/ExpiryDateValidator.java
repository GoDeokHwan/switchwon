package com.example.switchwonapi.global.support.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpiryDateValidator implements ConstraintValidator<ExpiryDate, String> {
    private boolean nullable = false;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (expiryDateValid(value)) {
            return true;
        }
        return false;
    }

    private boolean expiryDateValid(String value) {
        if (value == null || "".equals(value)) {
            if (nullable) {
                return true;
            } else {
                return false;
            }
        }
        String noHyphen = value.replaceAll("/", "");
        String regex = "^\\d{4}$";
        return noHyphen.matches(regex);
    }
    @Override
    public void initialize(ExpiryDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.nullable = constraintAnnotation.nullable();
    }
}
