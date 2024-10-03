package com.example.fanmon_be.domain.shop.cart.controller;

import com.example.fanmon_be.domain.shop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/shop/cart")
    public void cart(Model model) {
        model.addAttribute("cart",cartService.findAll());
    }
}
