package com.karol.hotelreservationsystem.annotations;

import com.karol.hotelreservationsystem.validators.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Invalid date format, expected format is dd-MM-yyyy HH:mm:ss";
    String pattern() default "dd-MM-yyyy HH:mm:ss";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
