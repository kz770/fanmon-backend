package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageSubscriber {

    private final ObjectMapper objectMapper; // ObjectMapper 주입

    public MessageSubscriber(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void onMessage(String message, String channel){
        // JSON 형식으로 수신한 메시지를 객체로 변환
//        Message chatMessage = objectMapper.readValue(message, Message.class);
        System.out.println("Received Message : " + message);
        // 여기에서 chatMessage를 사용하여 필요한 로직 추가
    }
}
