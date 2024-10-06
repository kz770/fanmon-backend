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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

//    @GetMapping("/shop/cart/list")
//    public ResponseEntity<List<Cart>> getAllCartsWithGoods(Model model) {
//        List<Cart> cartList = cartService.findAll();
//        return ResponseEntity.ok(cartList);
//    }

    //유저별 장바구니 리스트 출력
    @GetMapping("/shop/cart/list/{useruuid}")
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
}
