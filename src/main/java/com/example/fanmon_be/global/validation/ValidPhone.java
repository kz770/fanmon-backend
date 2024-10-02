package com.example.fanmon_be.global.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy= ValidPhoneValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface ValidPhone {
    String message() default "유효하지 않은 핸드폰 번호 입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
