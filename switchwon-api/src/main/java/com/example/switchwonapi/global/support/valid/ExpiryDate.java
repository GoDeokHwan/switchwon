package com.example.switchwonapi.global.support.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExpiryDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpiryDate {
    String message() default "유효한 만료기간이 아닙니다.";
    boolean nullable() default false;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
