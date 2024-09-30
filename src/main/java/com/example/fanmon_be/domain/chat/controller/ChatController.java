package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    ChatService service;

    // 연결 테스트 메서드
    @MessageMapping("/sendMessage") // client -> server : 해당 엔드포인트로 전송된 메세지를 받음
    @SendTo("/topic/messages")  // server -> client : 해당 엔드포인트를 구독하는 모든 클라이언트에게 전송
    public String connect(String message){
        System.out.println("==================");
        System.out.println("백엔드 : "+message);
        return "백엔드에서 받은 메세지 : "+message;
    }

    @MessageMapping("/sendMessage/{user}")
    @SendTo("/topic/messages/{user}")
    public String sending(@DestinationVariable String user, String message){
        // jpa message 저장 로직 추가 예정
        System.out.println(user+"'s message = " + message);
        return "private message : "+message;
    }
}
