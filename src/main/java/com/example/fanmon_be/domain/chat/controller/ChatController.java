package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.entity.Message;
import com.example.fanmon_be.domain.chat.entity.Subscribe;
import com.example.fanmon_be.domain.chat.service.ChatService;
import com.example.fanmon_be.domain.chat.service.MessageService;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;
    @Autowired
    SubscribeService subscribeService;
    @Autowired
    UserDAO userDAO;
    
    private MessageService messageService;
    
    // 임시 세션유지
    public UUID useruuid = UUID.fromString("0cf55a0d-a2a5-443b-af46-835d70874c40");

    @MessageMapping("/{artistuuid}")
    @SendTo("/sub/{artistuuid}")
    public Message sendArtistMessage(@Payload Message message, @DestinationVariable UUID artistuuid) {
        messageService.save(message);
        return message;
    }
    @MessageMapping("/{artistuuid}/{useruuid}")
    @SendTo("/sub/{artistuuid}/{useruuid}")
    public Message sendFanMessage(@Payload Message message, @DestinationVariable UUID artistuuid, @DestinationVariable UUID useruuid) {
        messageService.save(message);
        return message;
    }

    // 임시 세션 저장 메서드
    @GetMapping("/chat/login")
    @ResponseBody
    public String login(HttpSession session){
        User user=userDAO.findById(useruuid).get();
        System.out.println("user = " + user);
        session.setAttribute("user", user);
        return "login success";
    }
    @GetMapping("/chat/logout")
    @ResponseBody
    public String logout(HttpSession session){

        session.removeAttribute("user");
        return "logout success";
    }

    // 구독 확인 메서드
    @ResponseBody
    @GetMapping("/chat/subscribe/{chatuuid}")
    public Subscribe subscriptionCheck(@PathVariable UUID chatuuid, HttpSession session){
        User user=(User)session.getAttribute("user");
        // subscribe 테이블에서 사용자가 해당 artist를 구독중인치 체크.
        Subscribe subscription = subscribeService.getSubscription2(user.getUseruuid(), chatuuid);
        if (subscription!=null){
            // 구독중이라면 구독 데이터 반환
            return subscription;
        }
        // 만약 아니라면 널 값을 반환
        return null;
    }
}
