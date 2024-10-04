package com.example.fanmon_be.domain.shop.goods.service;

import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.shop.goods.dao.GoodsDAO;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.shop.goods.enums.GoodsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GoodsViewService {

    @Autowired
    private GoodsDAO goodsDAO;
    @Autowired
    private TeamDAO teamDAO;

    //굿즈 전체 목록 출력
    public List<Goods> findByTeam(UUID teamuuid) {
        Team team = teamDAO.findById(teamuuid).orElse(null);
        if (teamuuid != null) {
            return goodsDAO.findByTeam(team);
        }
        return Collections.emptyList();
    }

    //굿즈 카테고리별 목록 출력
    public List<Goods> findByTeamuuidAndCategory(UUID teamuuid, GoodsCategory category) {
        Team team = teamDAO.findById(teamuuid).orElse(null);
        if (teamuuid != null && category != null) {
            return goodsDAO.findByTeamAndCategory(team, category);
        }
        return Collections.emptyList();
    }

    //굿즈 상세보기 출력
    public Goods findById(UUID uuid){
        return goodsDAO.findById(uuid).orElseThrow(()->{
            throw new NoSuchElementException(uuid+"상품을 찾을 수 없습니다.");
        });
    }
}
