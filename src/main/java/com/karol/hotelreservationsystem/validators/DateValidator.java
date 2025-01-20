package com.karol.hotelreservationsystem.validators;

import com.karol.hotelreservationsystem.annotations.ValidDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator implements ConstraintValidator<ValidDate, String> {
    private String pattern;

    @Override
    public void initialize(ValidDate annotation) {
        this.pattern = annotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            formatter.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}