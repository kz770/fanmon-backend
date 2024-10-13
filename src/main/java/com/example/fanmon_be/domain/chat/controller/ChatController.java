package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.entity.Chat;
import com.example.fanmon_be.domain.chat.entity.Subscribe;
import com.example.fanmon_be.domain.chat.service.ChatService;
import com.example.fanmon_be.domain.chat.service.MessageService;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.enums.UserStatus;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
public class ChatController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private ChatService chatService;


    // 불량 사용자 차단
    @Transactional
    @PostMapping("/chat/block")
    public UUID block(@RequestParam String uuid){
        UUID useruuid = UUID.fromString(uuid);
        User user=userDAO.findById(useruuid).get();
        user.setStatus(UserStatus.BANNED);
        System.out.println("user = " + user);
        return useruuid;
    }

    //구독 리스트를 반환
    @GetMapping("/chat/chatlist/{useruuid}")
    public ResponseEntity<List<Subscribe>> mySubscriptionList(@PathVariable UUID useruuid) {
        List<Subscribe> subscriptions = subscribeService.mySubscriptionList(useruuid);
        if (subscriptions.isEmpty()){
            System.out.println("구독 리스트가 존재하지 않음");
            // 구독 리스트가 없을 때 204(no content)를 보내준다
            return ResponseEntity.noContent().build();
        }
        System.out.println("구독리스트 존재!");
        return ResponseEntity.ok(subscriptions);
    }

    // 채팅 정보를 반환
    @GetMapping("/chat/{chatuuid}")
    public ResponseEntity<Chat> ChatRoom(@PathVariable UUID chatuuid){
        return ResponseEntity.ok(chatService.findById(chatuuid));
    }


    //메세지 리스트를 반환
    @ResponseBody
    @GetMapping("/chat/messages/{chatuuid}")
    public List<Object> list(@PathVariable UUID chatuuid){
        // DB에 저장된 메세지 불러오기
        return messageService.getAllMessages(chatuuid);
    }

    // 아티스트로 채팅 정보 검색
    @ResponseBody
    @GetMapping("/chat/chatinfo/{artistuuid}")
    public Chat findChat(@PathVariable UUID artistuuid) {
        return chatService.findChatInfo(artistuuid);
    }
}
