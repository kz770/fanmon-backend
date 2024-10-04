package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.MessageDAO;
import com.example.fanmon_be.domain.chat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public void save(Message message){
        messageDAO.save(message);
    }
}
