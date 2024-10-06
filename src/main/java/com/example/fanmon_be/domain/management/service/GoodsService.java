package com.example.fanmon_be.domain.management.service;

import com.example.fanmon_be.domain.artist.dao.TeamDAO;
import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.artist.service.TeamService;
import com.example.fanmon_be.domain.management.dao.ManagementDAO;
import com.example.fanmon_be.domain.management.entity.Management;
import com.example.fanmon_be.domain.shop.goods.dao.GoodsDAO;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GoodsService {
    @Autowired
    private GoodsDAO goodsDAO;
    @Autowired
    private ManagementDAO managementDAO;
    @Autowired
    private TeamDAO teamDAO;

    public List<Goods> getAllGoods() { return goodsDAO.findAll(); }

    //Goods CREATE
    public Goods createGoods(Goods goods) {
        //Management 객체 조회
        Management management = managementDAO.findByManagementuuid(goods.getManagement().getManagementuuid());
//        if (management == null) {} 나중에 예외처리
        Team team = teamDAO.findByTeamuuid(goods.getTeam().getTeamuuid());
//        if(team == null) {} 나중에 예외처리
        goods.setManagement(management);
        goods.setTeam(team);
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
