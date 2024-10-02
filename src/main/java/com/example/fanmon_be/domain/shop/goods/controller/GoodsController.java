package com.example.fanmon_be.domain.shop.goods.controller;

import com.example.fanmon_be.domain.shop.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    //굿즈 목록 출력
    @GetMapping("/shop/goods")
    public void findAll(Model model) {
        model.addAttribute("goods", goodsService.findAll());
    }

    //굿즈 상세 정보 출력
    @GetMapping("/shop/goods/detail")
    public String findByID(UUID uuid, Model model) {
        model.addAttribute("goods", goodsService.findById(uuid));
        return "/shop/goods/detail";
    }
}
