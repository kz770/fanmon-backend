package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.ChatDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatDAO dao;
}
