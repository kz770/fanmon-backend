package com.example.fanmon_be.domain.meeting.dao;

import com.example.fanmon_be.domain.meeting.entity.Stayuserlist;
import com.example.fanmon_be.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StayuserlistDAO extends JpaRepository<Stayuserlist, UUID> {
    @Query(value = "select u.* from orders o, user u, ordersdetail od, goods g, team t where u.useruuid = o.useruuid and o.ordersuuid = od.ordersuuid and g.goodsuuid = od.goodsuuid and g.teamuuid = t.teamuuid and t.name = :teamName order by RAND() LIMIT 1", nativeQuery = true)
    User findRandomUser(@Param("teamName") String teamName);

    @Query(value = "select count(distinct u.useruuid) from orders o, user u, ordersdetail od, goods g, team t where u.useruuid = o.useruuid and o.ordersuuid = od.ordersuuid and g.goodsuuid = od.goodsuuid and g.teamuuid = t.teamuuid and t.name = '정해인'", nativeQuery = true)
    int randomUserMaxNum(@Param("teamName") String teamName);
}
