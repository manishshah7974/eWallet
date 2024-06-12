package com.fintech.eWallet.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Step 1: Create a custom annotation
@Constraint(validatedBy = PANValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPAN {
    String message() default "Invalid PAN format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
