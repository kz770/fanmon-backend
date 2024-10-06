package com.example.fanmon_be.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Errorresponse {
    private String message;
}

//일부러 response 소문자로 썼습니다 (변경금지)
