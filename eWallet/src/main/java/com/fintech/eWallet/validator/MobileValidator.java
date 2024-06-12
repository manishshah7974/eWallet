package com.fintech.eWallet.validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<ValidMobile, String> {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");

    @Override
    public void initialize(ValidMobile constraintAnnotation) {
    }

    @Override
    public boolean isValid(String mobile, ConstraintValidatorContext context) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }
}
