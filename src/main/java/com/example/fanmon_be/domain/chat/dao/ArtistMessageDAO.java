package com.example.fanmon_be.domain.chat.dao;

import com.example.fanmon_be.domain.artist.entity.Artist;
import com.example.fanmon_be.domain.chat.entity.ArtistMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ArtistMessageDAO extends JpaRepository<ArtistMessage, Long> {
    public List<ArtistMessage> findByChatChatuuid(UUID chatuuid);
    public UUID findChatChatuuidByArtistArtistuuid(UUID artistuuid);

}

