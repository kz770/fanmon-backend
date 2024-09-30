package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.entity.Subscribe;
import com.example.fanmon_be.domain.chat.service.ChatService;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;
    @Autowired
    SubscribeService subscribeService;

    // 연결 테스트 메서드
    @MessageMapping("/sendMessage") // client -> server : 해당 엔드포인트로 전송된 메세지를 받음
    @SendTo("/topic/messages")  // server -> client : 해당 엔드포인트를 구독하는 모든 클라이언트에게 전송
    public String connect(String message){
        System.out.println("==================");
        System.out.println("백엔드 : "+message);
        return "백엔드에서 받은 메세지 : "+message;
    }

    @ResponseBody
    @GetMapping("/chat/subscribe/{artistuuid}")
    public String subscriptionCheck(@DestinationVariable UUID artistuuid){
        UUID useruuid=UUID.randomUUID();
        artistuuid=UUID.randomUUID();
        // subscribe 테이블에서 사용자가 해당 artist를 구독중인치 체크.
        Subscribe subscription = subscribeService.getSubscription(useruuid, artistuuid);
        if (subscription!=null){
            return subscription.getUser().getName();
        }
        // 만약 아니라면 다른 페이지로 리다이렉트 시킨다.
        return "찾는 구독 정보가 없습니다!";    // 구독중이라면 해당 아티스트와의 웹소켓 통신 연결
    }

    @MessageMapping("/sendMessage/{user}")
    @SendTo("/sub/messages/{user}")
    public String sending(@DestinationVariable String user, String message){
        // jpa message 저장 로직 추가 예정
        System.out.println(user+"'s message = " + message);
        return "private message : "+message;
    }

    @MessageMapping("/{artistuuid}")
    @SendTo("/sub/{artistuuid}")
    public Message artistMessage(@DestinationVariable String artistuuid, Message message){
        // 유저가 보낸 메세지를 아티스트에게 전달하는 메서드
        // 유저가 구독한 artist의 uuid를 구독 테이블에서 확인하고

        return message;
    }
    @MessageMapping("/{artistuuid}/{useruuid}")
    @SendTo("/sub/{artistuuid}/{useruuid}")
    public Message userMessage(@DestinationVariable String artistuuid, Message message){
        // 유저가 보낸 메세지를 아티스트에게 전달하는 메서드
        // 유저가 구독한 artist의 uuid를 구독 테이블에서 확인하고

        return message;
    }
}
