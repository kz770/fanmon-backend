package com.example.fanmon_be.domain.shop.buy.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/shop/buy")
public class BuyController {

    @PostMapping("/buying")
    public ResponseEntity<?> handleBuying(@RequestBody Map<String, Object> requestBody, HttpSession session) {
        try {
            // 주문 정보 및 세부 정보 추출
            Map<String, Object> ordersData = (Map<String, Object>) requestBody.get("orders");
            List<Map<String, Object>> ordersDetailList = (List<Map<String, Object>>) requestBody.get("ordersDetailList");

            // 결제 정보를 세션에 저장
            Map<String, Object> paymentData = new HashMap<>();
            paymentData.put("orders", ordersData);
            paymentData.put("ordersDetailList", ordersDetailList);
            session.setAttribute("paymentData", paymentData);

            System.out.println(paymentData);

            // JSON 데이터로 반환
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(paymentData); // paymentData를 직접 반환

        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("데이터 타입 오류가 발생했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
        }
    }

}

