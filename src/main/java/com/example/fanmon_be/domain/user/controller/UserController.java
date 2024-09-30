package com.example.fanmon_be.domain.user.controller;

import com.example.fanmon_be.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/test2")
    public String test(){
        return "test";
    }
}
