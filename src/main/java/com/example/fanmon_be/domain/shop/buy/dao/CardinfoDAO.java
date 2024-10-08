package com.example.fanmon_be.domain.shop.buy.dao;

import com.example.fanmon_be.domain.shop.buy.entity.Cardinfo;
import com.example.fanmon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardinfoDAO extends JpaRepository<Cardinfo, UUID>{

//    List<Cardinfo> findByUser(User user);
    @Query("SELECT c.cardinfouuid, c.user.useruuid, c.orders.ordersuuid, c.approval, c.brand, c.number, c.currency, c.totalcost, c.provider, c.type FROM Cardinfo c WHERE c.user.useruuid = :useruuid")
    Cardinfo findByUseruuid(UUID useruuid);
}


