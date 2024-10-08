package com.example.fanmon_be.domain.artist.dao;

import com.example.fanmon_be.domain.artist.entity.Team;
import com.example.fanmon_be.domain.management.entity.Management;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamDAO extends JpaRepository<Team, UUID> {
    //Managementuuid로 소속 팀 찾기
    List<Team> findByManagementManagementuuid(UUID managementuuid);

    Team findByTeamuuid(UUID teamuuid);

}