package com.example.fanmon_be.domain.meeting.dao;

import com.example.fanmon_be.domain.meeting.entity.Stayroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StayroomDAO extends JpaRepository<Stayroom, UUID> {
    @Query(value = "select count(distinct u.useruuid) from orders o, user u, ordersdetail od, goods g, team t where u.useruuid = o.useruuid and o.ordersuuid = od.ordersuuid and g.goodsuuid = od.goodsuuid and g.teamuuid = t.teamuuid and t.name = :teamName", nativeQuery = true)
    int randomUserMaxNum(@Param("teamName") String teamName);
}