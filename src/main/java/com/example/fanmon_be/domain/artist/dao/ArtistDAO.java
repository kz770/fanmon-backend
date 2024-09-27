package com.example.fanmon_be.domain.artist.dao;

import com.example.fanmon_be.domain.artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistDAO extends JpaRepository<Artist, Long> {
}
