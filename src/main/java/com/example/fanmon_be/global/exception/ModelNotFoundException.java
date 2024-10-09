package com.example.fanmon_be.global.exception;


public class ModelNotFoundException extends RuntimeException {
    public ModelNotFoundException(String model) {
        super(model + "존재하지 않습니다");
    }
}