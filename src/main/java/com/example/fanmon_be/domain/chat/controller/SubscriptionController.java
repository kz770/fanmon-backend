package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.entity.Chat;
import com.example.fanmon_be.domain.chat.entity.Subscribe;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import com.example.fanmon_be.domain.user.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.Flow;

@RestController
@RequestMapping("/chat/subcription")
public class SubscriptionController {
    @Autowired
    SubscribeService subscribeService;

//    @GetMapping("/{useruuid}")
//    public ResponseEntity<Subscribe> checkSubscriptionValidation(
//            @PathVariable UUID useruuid
//    ){
//        // 1. useruuid로 구독 객체를 가져온다.
//        // 2. 구독객체의 상태를 체크한다.
//        // SUBSCRIBED && 만료날짜 전 -> ok
//        // UNSUBSCRIBED && 만료날짜 전 -> ok
//        // UNSUBSCRIBED && 만료날짜 후 -> 구독해지함
//        // EXPIRED -> 결제하시겠습니까?
//        return ResponseEntity.ok()
//    }

    // 구독 확인 메서드
    @ResponseBody
    @GetMapping("{chatuuid}")
    public ResponseEntity<Subscribe> subscriptionCheck(@PathVariable UUID chatuuid, HttpSession session){
        User user=(User)session.getAttribute("user");
        // subscribe 테이블에서 사용자가 해당 artist를 구독중인치 체크.
        Subscribe subscription = subscribeService.getSubscription2(user.getUseruuid(), chatuuid);
        if (subscription!=null){
            // 구독중이라면 구독 데이터 반환
            return ResponseEntity.ok(subscription);
        }
        // 만약 아니라면 널 값을 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
