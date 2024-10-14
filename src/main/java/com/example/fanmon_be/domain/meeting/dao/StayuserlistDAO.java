package com.example.fanmon_be.domain.meeting.dao;

import com.example.fanmon_be.domain.meeting.entity.Stayuserlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StayuserlistDAO extends JpaRepository<Stayuserlist, UUID> {
    @Query(value = "select u.useruuid from orders o, user u, ordersdetail od, goods g, team t where u.useruuid = o.useruuid and o.ordersuuid = od.ordersuuid and g.goodsuuid = od.goodsuuid and g.teamuuid = t.teamuuid and t.name = :teamName order by RAND() LIMIT 1", nativeQuery = true)
    byte[] findRandomUserID(@Param("teamName") String teamName);

    List<Stayuserlist> findByStayroom_StayuuidOrderByNo(UUID stayuuid);
}
