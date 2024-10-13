package com.example.fanmon_be.domain.shop.buy.controller;

import com.example.fanmon_be.domain.shop.buy.entity.Orders;
import com.example.fanmon_be.domain.shop.buy.entity.Ordersdetail;
import com.example.fanmon_be.domain.shop.buy.enums.OrdersStatus;
import com.example.fanmon_be.domain.shop.buy.service.CardinfoService;
import com.example.fanmon_be.domain.shop.buy.service.OrdersService;
import com.example.fanmon_be.domain.shop.buy.service.OrdersdetailService;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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
    

    //결제 성공
    // Orders 테이블에 데이터 저장
    @PostMapping("/bought/sendO/{useruuid}")
    public ResponseEntity<Void> handleSendO(@PathVariable String useruuid, @RequestBody HashMap<String, Object> request) {
        System.out.println("Received data(O): " + request);

        Orders orders = new Orders();

//      orders.setImpuid(request.get("imp_uid").toString());
//      System.out.println(request.get("imp_uid"));

        orders.setApplynum(request.get("apply_num").toString());
        System.out.println(request.get("apply_num").toString());

        orders.setMerchantuid(request.get("merchant_uid").toString());
        System.out.println(request.get("merchant_uid").toString());

        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> userDataMap = (LinkedHashMap<String, Object>) request.get("user_data");
        User user = mapper.convertValue(userDataMap, User.class);
        String birthString = userDataMap.get("birth").toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식에 맞게 설정
        LocalDate birthDate = LocalDate.parse(birthString, formatter);
        user.setBirth(birthDate); // LocalDate로 설정
        orders.setUser(user);
        System.out.println(user);

        orders.setAddress(request.get("buyer_addr").toString());
        System.out.println(request.get("buyer_addr").toString());

        orders.setTotalcost(((Number) request.get("paid_amount")).longValue());
        System.out.println(((Number) request.get("paid_amount")).longValue());

        orders.setCreatedat(LocalDateTime.parse(request.get("paid_at").toString()));
        System.out.println(LocalDateTime.parse(request.get("paid_at").toString()));

        orders.setStatus(OrdersStatus.valueOf("BOUGHT"));
        System.out.println(OrdersStatus.valueOf("BOUGHT"));

        orders.setQty(((Number) request.get("paid_qty")).longValue());
        System.out.println(((Number) request.get("paid_qty")).longValue());

        ordersService.save(orders);

        System.out.println(orders);
        // 저장 끝났으면 ok사인!
        return ResponseEntity.ok().build();
    }

    // Orders Detail 테이블에 데이터 저장
    @PostMapping("/bought/sendD/{useruuid}")
    public ResponseEntity<Void> handleSendD(@PathVariable String useruuid, @RequestBody HashMap<String, Object> request) {
        System.out.println("Received data(D): " + request);

        List<Map<String, Object>> Details = (List<Map<String, Object>>) request.get("DetailData");
        for (Map<String, Object> detail : Details) {
            Ordersdetail odetails = new Ordersdetail();

            ObjectMapper mapperU = new ObjectMapper();
            LinkedHashMap<String, Object> userDataMap = (LinkedHashMap<String, Object>) request.get("user_data");
            User user = mapperU.convertValue(userDataMap, User.class);
            String birthString = userDataMap.get("birth").toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식에 맞게 설정
            LocalDate birthDate = LocalDate.parse(birthString, formatter);
            user.setBirth(birthDate); // LocalDate로 설정
            odetails.setUser(user);
            System.out.println(user);

            ObjectMapper mapperO = new ObjectMapper();
            LinkedHashMap<String, Object> orderDataMap = (LinkedHashMap<String, Object>) request.get("orders_data");
            Orders order = mapperO.convertValue(userDataMap, Orders.class);
            user = order.getUser();
            userDataMap = (LinkedHashMap<String, Object>) orderDataMap.get("user_data");
            birthString = userDataMap.get("birth").toString();
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식에 맞게 설정
            birthDate = LocalDate.parse(birthString, formatter);
            user.setBirth(birthDate); // LocalDate로 설정
            odetails.setOrders(order);
            System.out.println(order);

            ObjectMapper mapperG = new ObjectMapper();
            LinkedHashMap<String, Object> goodDataMap = (LinkedHashMap<String, Object>) request.get("goods_data");
            Goods good = mapperG.convertValue(userDataMap, Goods.class);
            user = order.getUser();
            userDataMap = (LinkedHashMap<String, Object>) goodDataMap.get("user_data");
            birthString = goodDataMap
                    .get("birth").toString();
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식에 맞게 설정
            birthDate = LocalDate.parse(birthString, formatter);
            user.setBirth(birthDate); // LocalDate로 설정
            odetails.setGoods(good);
            System.out.println(good);

            odetails.setTotalcost(((Number) detail.get("detail_amount")).longValue());
            System.out.println(((Number) detail.get("detail_amount")).longValue());

            odetails.setQty(((Number) detail.get("detail_qty")).longValue());
            System.out.println(((Number) detail.get("detail_qty")).longValue());
        }

        System.out.println(Details);

        // 저장 끝났으면 ok사인!
        return ResponseEntity.ok().build();
    }


}


