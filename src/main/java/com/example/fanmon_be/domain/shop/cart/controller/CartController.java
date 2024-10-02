package com.example.fanmon_be.domain.shop.cart.controller;

import ch.qos.logback.core.model.Model;
import com.example.fanmon_be.domain.shop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/shop/cart")
    public String cart(UUID uuid, Model model) {

    }
}
