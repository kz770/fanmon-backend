package com.example.fanmon_be.global.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("이미 존재하는 이메일 입니다.");
    }
}
