package com.example.fanmon_be.domain.management.service;

import com.example.fanmon_be.domain.shop.goods.dao.GoodsDAO;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GoodsService {
    @Autowired
    private GoodsDAO goodsDAO;

    public Goods createGoods(Goods goods) {
        return goodsDAO.save(goods);
    }

    public Goods updateGoods(UUID goodsuuid, Goods goods) {
        goods.setGoodsuuid(goodsuuid);
        return goodsDAO.save(goods);
    }

    public void deleteGoods(UUID goodsuuid) {
        goodsDAO.deleteById(goodsuuid);
    }

    public Goods getGoodsById(UUID goodsuuid) {
        return goodsDAO.findById(goodsuuid).orElse(null);
    }
}
