package com.example.fanmon_be.domain.chat.dao;

import com.example.fanmon_be.domain.chat.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserMessageDAO extends JpaRepository<UserMessage, UUID> {
    public List<UserMessage> findByChatChatuuid(UUID uuid);
}
