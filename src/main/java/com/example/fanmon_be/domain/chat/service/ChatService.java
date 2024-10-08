package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.ChatDAO;
import com.example.fanmon_be.domain.chat.entity.Chat;
import com.example.fanmon_be.domain.user.entity.User;
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
}
