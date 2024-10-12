package com.example.fanmon_be.domain.chat.dao;

import com.example.fanmon_be.domain.chat.entity.Chat;
import com.example.fanmon_be.domain.chat.entity.Subscribe;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SubscribeDAO extends JpaRepository<Subscribe, UUID> {
    // 구독정보 반환
    Subscribe findByUserUseruuidAndChatArtistArtistuuid(UUID useruuid, UUID artistuuid);
    Subscribe findByUserUseruuidAndChatChatuuid(UUID useruuid, UUID chatuuid);

    List<Subscribe> findByUserUseruuid(UUID uuid);

}
