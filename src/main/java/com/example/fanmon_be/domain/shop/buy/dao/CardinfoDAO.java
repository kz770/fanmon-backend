package com.example.fanmon_be.domain.shop.buy.dao;

import com.example.fanmon_be.domain.shop.buy.entity.Cardinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CardinfoDAO extends JpaRepository<Cardinfo, UUID>{

    //아니 이거 정답이 뭐임
    @Query("SELECT c FROM Cardinfo c WHERE c.useruuid = HEX(:useruuid))
    List<Cardinfo> findByUseruuid(UUID useruuid);
}


