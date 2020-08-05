package com.simo.web.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldMatchValidator.class)
public @interface FieldMatch {

    String message() default "The two fields do not match";
    String first();
    String second();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

}
