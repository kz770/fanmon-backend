package com.example.fanmon_be.domain.shop.goods.controller;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.artist.service.TeamService;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.shop.goods.enums.GoodsCategory;
import com.example.fanmon_be.domain.shop.goods.service.GoodsViewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class GoodsViewController {

    @Autowired
    private GoodsViewService goodsViewService;

    @Autowired
    private TeamService teamService;

    //굿즈 팀별 출력
    @GetMapping("/shop/goods")
    public ResponseEntity<List<Team>> findAllCategory() {
        List<Team> category = teamService.findAll();
        return ResponseEntity.ok(category);
    }

    //굿즈 카테고리 출력
    @GetMapping("/shop/goods/category")
    public ResponseEntity<List<String>> getCategory() {
        List<String> categories = Arrays.stream(GoodsCategory.values())
            .map(Enum::name)
            .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    //굿즈 목록 출력
    @GetMapping("/shop/goods/list")
    public ResponseEntity<List<Goods>> findAllList() {
        List<Goods> goodsList = goodsViewService.findAll();
        return ResponseEntity.ok(goodsList);
    }

    // 굿즈 상세 정보 출력
    @GetMapping("/shop/goods/detail/{goodsuuid}")
    @ResponseBody
    public ResponseEntity<Goods> findByID(@PathVariable UUID goodsuuid, HttpSession session) {
        session.setAttribute("goodsuuid", goodsuuid);
        Goods goods = goodsViewService.findById(goodsuuid);
        return ResponseEntity.ok(goods);
    }
}