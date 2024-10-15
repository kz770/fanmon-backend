package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.entity.Chat;
import com.example.fanmon_be.domain.chat.entity.Subscribe;
import com.example.fanmon_be.domain.chat.enums.SubscriptionStatus;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import com.example.fanmon_be.domain.user.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Flow;

@RestController
@RequestMapping("/chat/subscription")
public class SubscriptionController {
    @Autowired
    SubscribeService subscribeService;

    // 구독 확인 메서드
    @ResponseBody
    @GetMapping("/check/{chatuuid}")
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

    //구독 리스트를 반환
    @GetMapping("/{useruuid}")
    public ResponseEntity<List<Subscribe>> mySubscriptionList(@PathVariable UUID useruuid) {
        // 1. useruuid로 구독 객체를 가져온다.
        List<Subscribe> subscriptions = subscribeService.mySubscriptionList(useruuid);
        if (subscriptions.isEmpty()){
            System.out.println("구독 리스트가 존재하지 않음");
            // 구독 리스트가 없을 때 204(no content)를 보내준다
            return ResponseEntity.noContent().build();
        }
//        List<Subscribe> checkedSubscriptions=new ArrayList<>();
//        // 2. 구독객체의 상태를 체크한다.
//        for(Subscribe subscription:subscriptions){
//            Enum<SubscriptionStatus> status=subscription.getSubscriptionStatus();
//            // SUBSCRIBED && 만료날짜 전 -> ok
//            if (status==SubscriptionStatus.SUBSCRIBED && subscription.getEndsubscription().isAfter(LocalDateTime.now())){
//                checkedSubscriptions.add(subscription);
//            // UNSUBSCRIBED && 만료날짜 전 -> ok
//            } else if(status==SubscriptionStatus.UNSUBSCRIBED && subscription.getEndsubscription().isAfter(LocalDateTime.now())){
//                checkedSubscriptions.add(subscription);
//            // UNSUBSCRIBED && 만료날짜 후 -> 구독해지함
//            } else if(status==SubscriptionStatus.UNSUBSCRIBED && subscription.getEndsubscription().isBefore(LocalDateTime.now())) {
//
//            // EXPIRED -> 결제하시겠습니까?
//            } else {
//
//            }
//        }
        System.out.println("구독리스트 존재!");
        return ResponseEntity.ok(subscriptions);
    }

    // 구독 해지
    @GetMapping("/unsubscribe/{subscribeuuid}")
    public void unsubscribe(@PathVariable UUID subscribeuuid){
        Optional<Subscribe> optionalSubscribe = subscribeService.findById(subscribeuuid);
        if (optionalSubscribe.isPresent()) {
            Subscribe subscribe = optionalSubscribe.get();
            subscribe.setSubscriptionStatus(SubscriptionStatus.UNSUBSCRIBED);
            subscribeService.save(subscribe);  // 변경된 엔티티를 저장
        }
        System.out.println("optionalSubscribe = " + optionalSubscribe);
    }

    // 구독
    @PostMapping("/newsubscription")
    public void newsubscription(@RequestBody Subscribe subscribe){
        subscribe.setStartsubscription(LocalDateTime.now());
        subscribeService.save(subscribe);
        System.out.println("subscribe = " + subscribe);
    }
}
