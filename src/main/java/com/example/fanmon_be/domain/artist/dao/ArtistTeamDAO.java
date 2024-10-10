package com.example.fanmon_be.domain.artist.dao;

import com.example.fanmon_be.domain.artist.entity.ArtistTeam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistTeamDAO extends JpaRepository<ArtistTeam, UUID> {
    List<ArtistTeam> findByTeamTeamuuid(UUID teamuuid);

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Artistteam where artistteamuuid = :uuid")
//    void deleteByArtistteamuuid(@Param("uuid") UUID uuid);
}
