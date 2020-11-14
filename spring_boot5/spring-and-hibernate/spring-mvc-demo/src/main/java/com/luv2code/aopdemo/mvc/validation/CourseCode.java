package com.luv2code.aopdemo.mvc.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CourseCodeContraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface CourseCode {

    // define default course code
    public String value() default "LUV";
    // define default error message
    public String message() default "must start with LUV";
    // define default groups
    // Groups: can group related constraints.
    public Class<?>[] groups() default {};
    // define default payloads
    // Payloads: provide custom details about validation failure (security level, error code etc)
    public Class<? extends Payload>[] payload() default{};
}
