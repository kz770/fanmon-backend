package com.example.fanmon_be.domain.artist.dao;

import com.example.fanmon_be.domain.artist.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtistDAO extends JpaRepository<Artist, UUID> {

    List<Artist> findArtistsByManagementManagementuuid(UUID managementuuid);

    Artist findByArtistuuid(UUID artistuuid);

    Optional<Artist> findByEmail(String email);

    @Query("select count(artistuuid) from Artist where management.managementuuid=:managementuuid")
    Long countByManagementManagementuuid(@Param("managementuuid") UUID managementuuid);

    @Transactional
    @Modifying
    @Query("delete from Artist where artistuuid=:artistuuid")
    void deleteByArtistuuid(@Param("artistuuid") UUID artistuuid);

    boolean existsByEmail(String email);
}
