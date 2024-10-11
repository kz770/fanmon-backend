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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    

    //결제 성공
    // Orders 테이블에 데이터 저장
    @PostMapping("/bought/sendO/{useruuid}")
    public ResponseEntity<Void> handleSendO(@PathVariable String useruuid, @RequestBody Map<String, Object> request) {
        Orders orders = new Orders();
        orders.setOrdersuuid((UUID) request.get("imp_uid"));
        orders.setApplynum((String) request.get("apply_num"));
        orders.setMerchantuid((String) request.get("merchant_uid"));
        orders.setUser((User) request.get("user_data"));
        orders.setAddress((String) request.get("buyer_addr"));
        orders.setTotalcost((long) request.get("paid_amount"));
        orders.setCreatedat((LocalDateTime)request.get("paid_at"));
        orders.setStatus(OrdersStatus.valueOf("BOUGHT"));
        orders.setQty((long) request.get("paid_qty"));

        // 저장 끝났으면 ok사인!
        return ResponseEntity.ok().build();
    }

    // Orders Detail 테이블에 데이터 저장
    @PostMapping("/bought/sendD/{useruuid}")
    public ResponseEntity<Void> handleSendD(@PathVariable String useruuid, @RequestBody Map<String, Object> request) {
        List<Map<String, Object>> Details = (List<Map<String, Object>>) request.get("");
        for (Map<String, Object> detail : Details) {
            Ordersdetail odetails = new Ordersdetail();
            odetails.setUser((User) detail.get(""));
            odetails.setOrders((Orders) detail.get(""));
            odetails.setGoods((Goods) detail.get(""));
            odetails.setTotalcost((long) detail.get(""));
            odetails.setQty((long) detail.get(""));
        }

        // 저장 끝났으면 ok사인!
        return ResponseEntity.ok().build();
    }


}


