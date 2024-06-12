package com.fintech.eWallet.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PANValidator implements ConstraintValidator<ValidPAN, String> {
    private static final String PAN_REGEX = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
    private static final Pattern PATTERN = Pattern.compile(PAN_REGEX);

    @Override
    public void initialize(ValidPAN constraintAnnotation) {
    }

    @Override
    public boolean isValid(String pan, ConstraintValidatorContext context) {
        return pan != null && PATTERN.matcher(pan).matches();
    }
}
