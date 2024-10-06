package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.MessageDAO;
import com.example.fanmon_be.domain.chat.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public void save(Message message){
        messageDAO.save(message);
    }
    public void saveAll(List<Message> messages){
        messageDAO.saveAll(messages);
    }
    public List<Message> findByChatuuid(UUID chatuuid){
        return messageDAO.findByChatChatuuid(chatuuid);
    }
}
