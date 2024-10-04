package com.example.fanmon_be.domain.shop.goods.controller;

import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.shop.goods.service.GoodsViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class GoodsViewController {

    @Autowired
    private GoodsViewService goodsViewService;

    //굿즈 목록 출력
    @GetMapping("/shop/goods")
    public ResponseEntity<List<Goods>> findAll() {
        List<Goods> goodsList = goodsViewService.findAll(); // 서비스로부터 데이터 가져오기
        return ResponseEntity.ok(goodsList); // JSON 형식으로 데이터 반환
    }

    //굿즈 상세 정보 출력
    @GetMapping("/shop/goods/detail/{uuid}")
    @ResponseBody
    public ResponseEntity<Goods> findByID(@PathVariable UUID uuid) {
        Goods goods = goodsViewService.findById(uuid);
        if (goods != null) {
            return ResponseEntity.ok(goods); // 성공적으로 찾은 경우 200 OK 응답
        } else {
            return ResponseEntity.notFound().build(); // 찾지 못한 경우 404 NOT FOUND 응답
        }
    }
}