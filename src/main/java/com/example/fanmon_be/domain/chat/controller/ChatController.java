package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.entity.Message;
import com.example.fanmon_be.domain.chat.service.ChatService;
import com.example.fanmon_be.domain.chat.service.MessagePublisher;
import com.example.fanmon_be.domain.chat.service.MessageService;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;
    @Autowired
    SubscribeService subscribeService;
    @Autowired
    private MessageService messageService;
    private final MessagePublisher publisher;
    
    public ChatController(MessagePublisher publisher){
        this.publisher=publisher;
    }

    @MessageMapping("/{artistuuid}")
    public void sendMessage(@Payload Message message, @DestinationVariable UUID artistuuid){
        System.out.println("message = " + message);
        messageService.save(message);
        publisher.publish(message);
    }

//    // 연결 테스트 메서드
//    @MessageMapping("/sendMessage") // client -> server : 해당 엔드포인트로 전송된 메세지를 받음
//    @SendTo("/sub/messages")  // server -> client : 해당 엔드포인트를 구독하는 모든 클라이언트에게 전송
//    public String connect(String message){
//        System.out.println("==================");
//        System.out.println("백엔드 : "+message);
//        return "백엔드에서 받은 메세지 : "+message;
//    }
//
//    @ResponseBody
//    @GetMapping("/chat/subscribe/{artistuuid}")
//    public Subscribe subscriptionCheck(@DestinationVariable UUID artistuuid){
//        UUID useruuid=UUID.randomUUID();
//        artistuuid=UUID.randomUUID();
//        // subscribe 테이블에서 사용자가 해당 artist를 구독중인치 체크.
//        Subscribe subscription = subscribeService.getSubscription(useruuid, artistuuid);
//        if (subscription!=null){
//            // 구독중이라면 구독 데이터 반환
//            return subscription;
//        }
//        // 만약 아니라면 널 값을 반환
//        return null;
//    }
//
//    @MessageMapping("/sendMessage/{user}")
//    @SendTo("/sub/messages/{user}")
//    public String sending(@DestinationVariable String user, String message){
//        // jpa message 저장 로직 추가 예정
//        System.out.println(user+"'s message = " + message);
//        return "private message : "+message;
//    }
//
//    @MessageMapping("/{artistuuid}")
//    @SendTo("/sub/{artistuuid}")
//    public Message artistMessage(@DestinationVariable String artistuuid, Message message){
//        // 유저가 보낸 메세지를 아티스트에게 전달하는 메서드
//        // 유저가 구독한 artist의 uuid를 구독 테이블에서 확인하고
//
//        return message;
//    }
//    @MessageMapping("/{artistuuid}/{useruuid}")
//    @SendTo("/sub/{artistuuid}/{useruuid}")
//    public Message userMessage(@DestinationVariable String artistuuid, Message message){
//        // 유저가 보낸 메세지를 아티스트에게 전달하는 메서드
//        // 유저가 구독한 artist의 uuid를 구독 테이블에서 확인하고
//        return message;
//    }
}
