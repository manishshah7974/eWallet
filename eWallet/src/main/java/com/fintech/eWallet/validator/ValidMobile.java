package com.fintech.eWallet.validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMobile {
    String message() default "Invalid Mobile Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
