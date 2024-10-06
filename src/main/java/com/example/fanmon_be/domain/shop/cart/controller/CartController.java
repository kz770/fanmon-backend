package com.example.fanmon_be.domain.shop.cart.controller;

import com.example.fanmon_be.domain.shop.cart.entity.Cart;
import com.example.fanmon_be.domain.shop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/shop/cart/list")
    public ResponseEntity<List<Cart>> getAllCartsWithGoods(Model model) {
        List<Cart> cartList = cartService.findAll();
        return ResponseEntity.ok(cartList);
    }
}
