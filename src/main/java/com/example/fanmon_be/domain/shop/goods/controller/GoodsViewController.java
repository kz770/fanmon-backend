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
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop/goods")
public class GoodsViewController {

    @Autowired
    private GoodsViewService goodsViewService;

    @Autowired
    private TeamService teamService;

    //팀별 굿즈샵 출력
    @GetMapping("/shop/goods/main")
    @GetMapping("/main")
    public ResponseEntity<List<Team>> findAllTeamList() {
        List<Team> teamlist = teamService.findAll();
        return ResponseEntity.ok(teamlist);
    }

    //굿즈 nav바 카테고리 목록 출력
    @GetMapping("/category")
    public ResponseEntity<List<String>> findAllCategory() {
        List<String> categories = Arrays.stream(GoodsCategory.values())
            .map(Enum::name)
            .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    //전체 굿즈 목록 출력
    @GetMapping("/list/{teamuuid}/all")
    public ResponseEntity<List<Goods>> findGoodsByCategory(@PathVariable UUID teamuuid, HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println(attributeName + ": " + session.getAttribute(attributeName));
        }

        System.out.println(session.getAttribute("category"));
        session.removeAttribute("category");
        session.setAttribute("teamuuid", teamuuid);
        List<Goods> goodsList = goodsViewService.findByTeam(teamuuid);
        return ResponseEntity.ok(goodsList);
    }

    //카테고리별 굿즈 목록 출력
    @GetMapping("/list/{teamuuid}/{category}")
    public ResponseEntity<List<Goods>> findGoodsByCategory(@PathVariable UUID teamuuid, @PathVariable String category, HttpSession session) {
        System.out.println(session.getAttribute("category"));
        GoodsCategory goodsCategory;
        try {
            goodsCategory = GoodsCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        session.setAttribute("teamuuid", teamuuid);
        session.setAttribute("category", goodsCategory);
        List<Goods> goodsList = goodsViewService.findByTeamAndCategory(teamuuid, goodsCategory);
        return ResponseEntity.ok(goodsList);
    }

    // 굿즈 상세 정보 출력
    @GetMapping("/detail/{goodsuuid}")
    @ResponseBody
    public ResponseEntity<Goods> findByID(@PathVariable UUID goodsuuid, HttpSession session) {
        session.setAttribute("goodsuuid", goodsuuid);
        Goods goods = goodsViewService.findById(goodsuuid);
        return ResponseEntity.ok(goods);
    }
}