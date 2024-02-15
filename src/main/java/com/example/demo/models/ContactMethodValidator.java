package com.example.demo.models;

import com.example.demo.models.enumerations.ContactType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactMethodValidator implements ConstraintValidator<ValidContactMethodValue, ContactMethod> {

    @JsonProperty("emailRegex")
    @Value("${emailRegex}")
    private String emailRegex;

    @JsonProperty("phoneRegex")
    @Value("${phoneRegex}")
    private String phoneRegex;

    @Override
    public void initialize(ValidContactMethodValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ContactMethod contactMethod, ConstraintValidatorContext constraintValidatorContext) {

        if (contactMethod == null || contactMethod.getValue() == null) {
            return false;
        }
        String value = contactMethod.getValue();
        Pattern pattern;

        if (contactMethod.getType() == ContactType.EMAIL) {
            pattern = Pattern.compile(emailRegex);
        } else if (contactMethod.getType() == ContactType.PHONE) {
            pattern = Pattern.compile(phoneRegex);
        } else {
            return false;
        }
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Invalid " + contactMethod.getType() + " format")
                    .addConstraintViolation();
            //constraintValidatorContext.disableDefaultConstraintViolation();
        }
        return matcher.matches();
    }
}
