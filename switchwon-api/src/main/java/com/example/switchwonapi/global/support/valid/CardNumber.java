package com.example.switchwonapi.global.support.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CardNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CardNumber {
    String message() default "유효한 카드가 아닙니다.";
    boolean nullable() default false;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
