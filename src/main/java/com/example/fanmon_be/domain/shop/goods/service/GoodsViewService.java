package com.example.fanmon_be.domain.shop.goods.service;

import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.shop.goods.dao.GoodsDAO;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
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

    //굿즈 목록 출력
    public List<Goods> findByTeamuuid(UUID teamuuid) {
        Team team = teamDAO.findById(teamuuid).orElse(null); // Team 객체 조회
        if (team != null) {
            return goodsDAO.findByTeam(team); // Team 객체로 굿즈 목록 조회
        }
        return Collections.emptyList(); // 팀이 존재하지 않을 경우 빈 리스트 반환
    }

    //굿즈 상세보기 출력
    public Goods findById(UUID uuid){
        return goodsDAO.findById(uuid).orElseThrow(()->{
            throw new NoSuchElementException(uuid+"상품을 찾을 수 없습니다.");
        });
    }
}
