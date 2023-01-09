package kl.project.webApp.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SurnameValidator implements ConstraintValidator<ValidSurname, String> {

    private static final String SURNAME_PATTERN = "^[A-ZĄĘĆŹŻÓŁŃ][a-ząęćźżółń]{1,20}(-[A-ZĄĘĆŹŻÓŁŃ][a-ząęćźżółń]{1,20})?";

    @Override
    public void initialize(ValidSurname constraintAnnotation) {
    }

    @Override
    public boolean isValid(String surname, ConstraintValidatorContext context) {
        return validateSurname(surname);
    }

    private boolean validateSurname(String surname) {
        Pattern pattern = Pattern.compile(SURNAME_PATTERN);
        Matcher matcher = pattern.matcher(surname);
        return matcher.matches();
    }
}
