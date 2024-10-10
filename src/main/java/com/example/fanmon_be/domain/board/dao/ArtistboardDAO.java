package com.example.fanmon_be.domain.board.dao;

import com.example.fanmon_be.domain.board.entity.Artistboard;
import com.example.fanmon_be.domain.board.entity.Fanboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistboardDAO extends JpaRepository<Artistboard, UUID> {

    public List<Artistboard> findArtistboardByTeamTeamuuid(UUID uuid);
    @Query("SELECT f FROM Artistboard f WHERE f.team.teamuuid = :uuid ORDER BY f.createdat")
    List<Artistboard> findArtistboardByTeamTeamuuidOrderByCreatedat(@Param("uuid") UUID uuid);
    @Transactional
    @Modifying
    @Query("DELETE FROM Artistboard where artistboarduuid = :uuid")
    void deleteByFanboarduuid(@Param("uuid") UUID uuid);
}
