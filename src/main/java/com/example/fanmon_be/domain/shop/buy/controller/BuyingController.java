package com.example.fanmon_be.domain.shop.buy.controller;

import com.example.fanmon_be.domain.shop.buy.entity.Cardinfo;
import com.example.fanmon_be.domain.shop.buy.service.CardinfoService;
import com.example.fanmon_be.domain.shop.buy.service.OrdersService;
import com.example.fanmon_be.domain.shop.buy.service.OrdersdetailService;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shop/buy")
public class BuyingController {

    @Autowired
    OrdersService ordersService;

    @Autowired
    OrdersdetailService ordersdetailService;

    @Autowired
    CardinfoService cardinfoService;

    @Autowired
    UserService userService;
//
//    // 결제시 유저 정보와 카드 정보를 불러오는 메소드
//    @PostMapping("/buying/{useruuid}")
//    public ResponseEntity<ResponseWrapper> getUserAndCardInfo(@PathVariable String useruuid) {
//        try{
//            System.out.println("usersuuid:"+useruuid);
//
//            User user = userService.getUserById(useruuid);
//            System.out.println("user:"+user);
//
//
//            List<Cardinfo> cardinfo = cardinfoService.findByUseruuid(useruuid);
//            System.out.println("cardinfo:"+cardinfo);
//
//            return ResponseEntity.ok(new ResponseWrapper(user, cardinfo));
//        } catch (Exception e) {
//            // 예외 로그 출력
//            e.printStackTrace();
//            System.out.println("Error fetching user and card info: " + e.getMessage());
//            e.printStackTrace(); // 예외 스택 추적 출력
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper(null, null));
//        }
//    }
//
//    // 결제에 필요한 데이터를 포장하는 메소드
//    public static class ResponseWrapper {
//        private User user;
//        private List<Cardinfo> cardinfo;
//
//        public ResponseWrapper(User user, List<Cardinfo> cardinfo) {
//            this.user = user;
//            this.cardinfo = cardinfo;
//        }
//    }
}


