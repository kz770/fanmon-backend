package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {

    @Autowired
    ChatService service;

    @GetMapping("/chat")
        public Map<String, Object> testHandler() {
            Map<String, Object> res = new HashMap<>();
            res.put("SUCCESS", true);
            res.put("SUCCESS_TEXT", "Hello SpringBoot/React");
            return res;
        }

}
