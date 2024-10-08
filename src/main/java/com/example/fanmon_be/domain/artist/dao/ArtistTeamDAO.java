package com.example.fanmon_be.domain.artist.dao;

import com.example.fanmon_be.domain.artist.entity.ArtistTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArtistTeamDAO extends JpaRepository<ArtistTeam, UUID> {
}
