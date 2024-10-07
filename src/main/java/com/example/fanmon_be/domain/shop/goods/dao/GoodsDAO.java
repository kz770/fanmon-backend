package com.example.fanmon_be.domain.shop.goods.dao;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.shop.goods.enums.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoodsDAO extends JpaRepository<Goods, UUID> {

    List<Goods> findByTeam(Team team);

    List<Goods> findByTeamAndCategory(Team team, GoodsCategory category);

    List<Goods> findByTeamTeamuuid(UUID teamuuid);
    @Modifying
    void deleteGoodsByGoodsuuid(UUID goodsuuid);
}
