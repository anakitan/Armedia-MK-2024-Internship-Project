package com.example.demo.models;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContactMethodValidator.class)
@Documented
public @interface ValidContactMethodValue {
    String message() default "Please enter a valid email and phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
