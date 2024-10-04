package com.example.fanmon_be.domain.chat.controller;

import com.example.fanmon_be.domain.chat.entity.Message;
import com.example.fanmon_be.domain.chat.service.ChatService;
import com.example.fanmon_be.domain.chat.service.MessageService;
import com.example.fanmon_be.domain.chat.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    ChatService chatService;
    @Autowired
    SubscribeService subscribeService;
    @Autowired
    private MessageService messageService;

    @MessageMapping("/{artistuuid}")
    @SendTo("/sub/{artistuuid}")
    public Message sendArtistMessage(@Payload Message message, @DestinationVariable UUID artistuuid) {
        messageService.save(message);
        return message;
    }
    @MessageMapping("/{artistuuid}/{useruuid}")
    @SendTo("/sub/{artistuuid}/{useruuid}")
    public Message sendFanMessage(@Payload Message message, @DestinationVariable UUID artistuuid, @DestinationVariable UUID useruuid) {
        messageService.save(message);
        return message;
    }

}
