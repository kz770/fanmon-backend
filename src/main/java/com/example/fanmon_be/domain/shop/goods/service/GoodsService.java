package com.example.fanmon_be.domain.shop.goods.service;

import com.example.fanmon_be.domain.shop.goods.dao.GoodsDAO;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GoodsService {

    @Autowired
    private GoodsDAO goodsDAO;

    //굿즈 목록 출력
    public List<Goods> findAll(){
        return goodsDAO.findAll();
//      return goodsDAO.selectALl();
    }

    //굿즈 상세보기 출력
    public Goods findById(UUID uuid){
        return goodsDAO.findById(uuid).orElseThrow(()->{
            throw new NoSuchElementException("상품을 찾을 수 없습니다."+uuid);
        });
    }
}
