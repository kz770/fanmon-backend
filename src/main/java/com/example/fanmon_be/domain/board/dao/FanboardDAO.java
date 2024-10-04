package com.example.fanmon_be.domain.board.dao;

import com.example.fanmon_be.domain.board.entity.Fanboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FanboardDAO extends JpaRepository<Fanboard, UUID> {
    public List<Fanboard> findFanboardByTeamTeamuuid(UUID uuid);
}
