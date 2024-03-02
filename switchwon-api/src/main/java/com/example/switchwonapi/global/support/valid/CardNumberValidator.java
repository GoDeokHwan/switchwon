package com.example.switchwonapi.global.support.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<CardNumber, String> {
    private boolean nullable = false;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (cardNumberValid(value)) {
            return true;
        }
        return false;
    }

    private boolean cardNumberValid(String value) {
        if (value == null || "".equals(value)) {
            if (nullable) {
                return true;
            } else {
                return false;
            }
        }
        String noHyphen = value.replaceAll("-", "");
        String regex = "^\\d{16}$";
        return noHyphen.matches(regex);
    }

    @Override
    public void initialize(CardNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.nullable = constraintAnnotation.nullable();
    }
}
