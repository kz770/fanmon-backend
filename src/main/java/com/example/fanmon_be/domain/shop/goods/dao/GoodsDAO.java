package com.example.fanmon_be.domain.shop.goods.dao;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.shop.goods.entity.Goods;
import com.example.fanmon_be.domain.shop.goods.enums.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoodsDAO extends JpaRepository<Goods, UUID> {

    List<Goods> findByTeam(Team team);

    List<Goods> findByTeamAndCategory(Team team, GoodsCategory category);

    List<Goods> findByTeamTeamuuid(UUID teamuuid);

    Goods findByGoodsuuid(UUID goodsuuid);

    @Query("select count(goodsuuid) from Goods where management.managementuuid=:managementuuid")
    Long countByManagementuuid(@Param("managementuuid") UUID managementuuid);

    @Transactional
    @Modifying
    @Query("delete from Goods where goodsuuid=:goodsuuid")
    void deleteByGoodsuuid(@Param("goodsuuid") UUID goodsuuid);
}
