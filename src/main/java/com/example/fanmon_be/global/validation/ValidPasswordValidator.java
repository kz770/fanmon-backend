package com.example.fanmon_be.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        return password.length() >= 8 && password.length() <= 16
                && password.matches(".*[a-zA-Z].*") // 영어 포함
                && password.matches(".*[0-9].*") // 숫자 포함
                && password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"); // 특수기호 포함
    }
}
