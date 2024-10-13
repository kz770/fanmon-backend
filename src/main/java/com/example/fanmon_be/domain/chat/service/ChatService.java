package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.ChatDAO;
import com.example.fanmon_be.domain.chat.entity.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    ChatDAO dao;

    public UUID findByArtistuuid(UUID artistuuid){
        return dao.findChatuuidByArtistArtistuuid(artistuuid);
    }
    public Chat findById(UUID uuid){
        Chat chat = dao.findById(uuid).get();
        return chat;
    }

    public Chat findChatInfo(UUID artistuuid){
        return dao.findByArtistArtistuuid(artistuuid);
    }
}
