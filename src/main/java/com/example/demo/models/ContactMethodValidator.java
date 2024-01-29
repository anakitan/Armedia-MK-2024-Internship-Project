package com.example.demo.models;

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
            return true;
        }
        String value = contactMethod.getValue();
        Pattern pattern;

        if (contactMethod.getType() == ContactMethod.ContactType.EMAIL) {
            pattern = Pattern.compile(emailRegex);
        } else if (contactMethod.getType() == ContactMethod.ContactType.PHONE) {
            pattern = Pattern.compile(phoneRegex);
        } else {
            return false;
        }
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
