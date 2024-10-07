package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.ArtistMessageDAO;
import com.example.fanmon_be.domain.chat.dao.UserMessageDAO;
import com.example.fanmon_be.domain.chat.entity.ArtistMessage;
import com.example.fanmon_be.domain.chat.entity.Chat;
import com.example.fanmon_be.domain.chat.entity.UserMessage;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageService {
    @Autowired
    private UserMessageDAO userMessageDAO;
    @Autowired
    private ArtistMessageDAO artistMessageDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 유저 메세지 메서드
    public void save(UserMessage message){
        userMessageDAO.save(message);
    }
    public void saveAllUserMessages(List<UserMessage> messages){
        userMessageDAO.saveAll(messages);
    }
    public List<UserMessage> findUserMessagesByChatuuid(UUID chatuuid){
        return userMessageDAO.findByChatChatuuid(chatuuid);
    }

    // 아티스트 메세지
    public void saveAllArtistMessages(List<ArtistMessage> messages) {
        artistMessageDAO.saveAll(messages);
    }
    public List<ArtistMessage> findArtistMessagesByChatuuid(UUID chatuuid) {
        return artistMessageDAO.findByChatChatuuid(chatuuid);
    }
    public UUID findChatuuid(UUID artistuuid){
        return artistMessageDAO.findChatChatuuidByArtistArtistuuid(artistuuid);
    }

    // 전체 합친 메세지
    public List<Object> getAllMessages(UUID chatUuid) {
        // 각각의 메시지 리스트를 가져옴
        List<UserMessage> userMessages = userMessageDAO.findByChatChatuuid(chatUuid);
        List<ArtistMessage> artistMessages = artistMessageDAO.findByChatChatuuid(chatUuid);
        List<Object> redisMessages = redisTemplate.opsForList().range("ARTIST"+chatUuid, 0,-1);

        // 두 리스트를 합침
        List<Object> allMessages = new ArrayList<>();
        allMessages.addAll(userMessages);
        allMessages.addAll(artistMessages);

        // 타임스탬프 기준으로 정렬 (내림차순)
        allMessages.sort((m1, m2) -> {
            LocalDateTime timestamp1 = getMessageTimestamp(m1);
            LocalDateTime timestamp2 = getMessageTimestamp(m2);
            return timestamp2.compareTo(timestamp1); // 내림차순 정렬
        });

        return allMessages;
    }
    
    // 타임스탬프 가져오기
    private LocalDateTime getMessageTimestamp(Object message) {
        if (message instanceof UserMessage) {
            return ((UserMessage) message).getTimestamp();
        } else if (message instanceof ArtistMessage) {
            return ((ArtistMessage) message).getTimestamp();
        }
        throw new IllegalArgumentException("메세지 타입 에러: " + message.getClass());
    }

    public static void main(String[] args) {
        UUID artistuuid=UUID.fromString("ca5a5a75-809c-11ef-b4db-0a2a78c30fc9");
        System.out.println("uuid 생성");
        UUID chatuuid=new MessageService().findChatuuid(artistuuid);

        System.out.println(chatuuid);
    }
}
