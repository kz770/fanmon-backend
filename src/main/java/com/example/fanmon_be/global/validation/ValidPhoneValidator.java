package com.example.fanmon_be.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPhoneValidator implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(String phone,  ConstraintValidatorContext context){
        if(phone == null){
            return false;
        }
        return phone.matches("^01[016789]-\\d{4}-\\d{4}$");
    }
}
