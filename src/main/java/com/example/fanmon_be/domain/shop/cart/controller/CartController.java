package com.example.fanmon_be.domain.shop.cart.controller;

import com.example.fanmon_be.domain.shop.cart.entity.Cart;
import com.example.fanmon_be.domain.shop.cart.service.CartService;
import com.example.fanmon_be.domain.user.entity.User;
import com.example.fanmon_be.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/shop/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //유저별 장바구니 리스트 출력
    @GetMapping("/list/{useruuid}")
    public ResponseEntity<List<Cart>> getCartByUser(@PathVariable UUID useruuid, HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println(attributeName + ": " + session.getAttribute(attributeName));
        }
        session.setAttribute("useruuid", useruuid);
        List<Cart> cartList = cartService.findByUser(useruuid);

        return ResponseEntity.ok(cartList);
    }

    // 장바구니에 새 상품 추가
    @PostMapping("/add/{useruuid}/{goodsuuid}/{qty}")
    public ResponseEntity<Boolean> inputQty(@PathVariable UUID useruuid, @PathVariable UUID goodsuuid, @PathVariable int qty) {

        boolean result = false;

        if (cartService.existsInCart(useruuid, goodsuuid)) { // 상품이 존재하는지 확인
            // 상품이 이미 장바구니에 존재하므로 수량만 변경
            cartService.plusQty(useruuid, goodsuuid, qty);
            result = true;
        } else {
            // 장바구니에 상품이 없으므로 레코드 생성
            cartService.inputQty(useruuid, goodsuuid, qty);
            result = true;
        }

        return ResponseEntity.ok(result); // 성공적인 응답 반환
    }


    // 장바구니에 담긴 상품의 수량 변경
    // CORS의 권한이 변경되면 PATCH로 바꿀게요
    @PostMapping("/update/{useruuid}/{goodsuuid}/{qty}")
    public void updateQty(@PathVariable UUID useruuid, @PathVariable UUID goodsuuid, @PathVariable long qty){
        cartService.updateQty(useruuid, goodsuuid, qty);
    }

    // 장바구니에 담긴 상품 삭제
    @GetMapping("/delete/{useruuid}/{cartsequence}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable UUID useruuid, @PathVariable Long cartsequence) {
        boolean result = cartService.deleteCartItem(useruuid, cartsequence);
        return ResponseEntity.ok(result);
    }
}
