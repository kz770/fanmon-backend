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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.ByteBuffer;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

        try {
            orders.setImpuid(request.get("imp_uid").toString());
            System.out.println("imp_uid: "+request.get("imp_uid"));

            orders.setMerchantuid(request.get("merchant_uid").toString());
            System.out.println("merchant_uid: "+request.get("merchant_uid").toString());

            orders.setApplynum(request.get("apply_num").toString());
            System.out.println("apply_num: "+request.get("apply_num").toString());

            long paidAtTime = ((Number) request.get("paid_at")).longValue();
            LocalDate paidAtDate = Instant.ofEpochSecond(paidAtTime)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            HashMap<String, Object> userDataMap = (HashMap<String, Object>) request.get("user_data");
            String birthString = (String) userDataMap.get("birth");
            LocalDate birthDate = LocalDate.parse(birthString); // 기본 형식 yyyy-MM-dd
            userDataMap.put("birth", birthDate); // User 엔티티에 맞게 변환된 값을 넣어줍니다.
            User user = mapper.convertValue(userDataMap, User.class);
            orders.setUser(user);
            System.out.println("user:"+user);

            orders.setAddress(request.get("buyer_addr").toString());
            System.out.println("buyer_addr: "+request.get("buyer_addr").toString());

            orders.setTotalcost(((Number) request.get("paid_amount")).longValue());
            System.out.println("paid_amount: "+((Number) request.get("paid_amount")).longValue());

            long paidAtTimestamp = Long.parseLong(request.get("paid_at").toString());
            LocalDateTime paidAtDateTime = LocalDateTime.ofEpochSecond(paidAtTimestamp, 0, ZoneOffset.UTC);
            orders.setCreatedat(paidAtDateTime);
            System.out.println("paid_at: " + paidAtDateTime);

            orders.setStatus(OrdersStatus.valueOf("BOUGHT"));
            System.out.println("status: "+OrdersStatus.valueOf("BOUGHT"));

            orders.setQty(((Number) request.get("paid_qty")).longValue());
            System.out.println("paid_qty: "+((Number) request.get("paid_qty")).longValue());

            ordersService.save(orders);

            System.out.println(orders);
            // 저장 끝났으면 ok사인!
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Orders Detail 테이블에 데이터 저장
    @PostMapping("/bought/sendD/{useruuid}")
    public ResponseEntity<Void> handleSendD(@PathVariable String useruuid, @RequestBody HashMap<String, Object> request) {
        System.out.println("Received data(D): " + request);
        List<Map<String, Object>> Details = (List<Map<String, Object>>) request.get("DetailData");
        try{

            for (Map<String, Object> detail : Details) {
                System.out.println("orders detail 루프 시작");

                try {
                    Ordersdetail odetails = new Ordersdetail();
                    ObjectMapper mapper = new ObjectMapper();
                    LinkedHashMap<String, Object> userDataMap = (LinkedHashMap<String, Object>) request.get("user_data");
                    User user = mapper.convertValue(userDataMap, User.class);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식에 맞게 설정
                    LocalDate birthDate = LocalDate.parse(userDataMap.get("birth").toString(), formatter);
                    user.setBirth(birthDate);
                    odetails.setUser(user);
                    System.out.println("user:"+user);

                    LinkedHashMap<String, Object> goodsDataMap = (LinkedHashMap<String, Object>)request.get("goods_data");
                    Goods goods = mapper.convertValue(goodsDataMap, Goods.class);
                    odetails.setGoods(goods);
                    System.out.println("goods: "+goods);

                    LinkedHashMap<String, Object> ordersDataMap = (LinkedHashMap<String, Object>)request.get("orders_data");
                    Orders orders = mapper.convertValue(ordersDataMap, Orders.class);
                    user = orders.getUser();
                    String birthString = ordersDataMap.get("birth").toString();
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 적절한 포맷 지정
                    birthDate = LocalDate.parse(birthString, formatter);
                    user.setBirth(birthDate);
                    odetails.setOrders(orders);
                    System.out.println("orders");

                    odetails.setTotalcost(((Number) detail.get("detail_amount")).longValue());
                    System.out.println("detail_amount: "+((Number) detail.get("detail_amount")).longValue());

                    odetails.setQty(((Number) detail.get("detail_qty")).longValue());
                    System.out.println("detail_qty: "+((Number) detail.get("detail_qty")).longValue());

                    System.out.println("odetails: "+odetails);
                } catch (Exception e) {
                    System.err.println("Error processing order: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }

            ordersdetailService.save((Ordersdetail) Details);

            System.out.println("Details: "+Details);

            // 저장 끝났으면 ok사인!
            return ResponseEntity.ok().build();

        }catch (Exception e) {
            System.err.println("Error processing order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}


