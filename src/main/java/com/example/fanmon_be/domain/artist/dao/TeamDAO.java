package com.example.fanmon_be.domain.artist.dao;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamDAO extends JpaRepository<Team, UUID> {
    //Managementuuid로 소속 팀 찾기
    List<Team> findByManagementManagementuuid(UUID managementuuid);

    Team findByTeamuuid(UUID teamuuid);

    @Query("select t from Team t where t.management.managementuuid = :managementuuid order by t.followers desc")
    List<Team> orderByFollowers(@Param("managementuuid") UUID managementuuid);

    @Query("select count(teamuuid) from Team where management.managementuuid=:managementuuid")
    Long countByManagementUuid(@Param("managementuuid") UUID managementuuid);
}