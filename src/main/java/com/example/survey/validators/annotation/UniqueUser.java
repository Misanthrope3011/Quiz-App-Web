package com.example.survey.validators.annotation;

import com.example.survey.validators.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = UniqueValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUser {

	String message() default "{User already exists}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
