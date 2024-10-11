package com.example.fanmon_be.domain.shop.buy.controller;

import com.example.fanmon_be.domain.shop.buy.entity.Cardinfo;
import com.example.fanmon_be.domain.shop.buy.service.CardinfoService;
import com.example.fanmon_be.domain.shop.buy.service.OrdersService;
import com.example.fanmon_be.domain.shop.buy.service.OrdersdetailService;
import com.example.fanmon_be.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    // 카드 정보를 불러오는 메소드
//    @PostMapping("/buying/card/{useruuid}")
//    public ResponseEntity<List<Cardinfo>> getUserAndCardInfo(@PathVariable String useruuid) {
//        try {
//            System.out.println("useruuid: " + useruuid);
//
//            // useruuid를 사용하여 카드 정보 조회
//            List<Cardinfo> cardinfoList = cardinfoService.findByUseruuid(useruuid);
//            System.out.println("cardinfo: " + cardinfoList);
//
//            // cardinfoList가 비어있으면 404 반환
//            if (cardinfoList.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//
//            return ResponseEntity.ok(cardinfoList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error fetching user and card info: " + e.getMessage());
//
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    //결제 성공
    @PostMapping("/bought/{useruuid}")
    public void successBuy(@PathVariable String useruuid) {

    }

}


