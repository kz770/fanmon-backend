package com.example.fanmon_be.domain.shop.buy.dao;

import com.example.fanmon_be.domain.shop.buy.entity.Cardinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface CardinfoDAO extends JpaRepository<Cardinfo, UUID>{

    //아니 이거 정답이 뭐임
    @Query(value = "SELECT cardinfouuid, c.useruuid, c.ordersuuid, approval, brand, number, currency, totalcost, provider, type FROM cardinfo c WHERE HEX(useruuid) = :useruuid", nativeQuery = true)
    List<Cardinfo> findByUseruuid(@Param("useruuid") String useruuid);

}


