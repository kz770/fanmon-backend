package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.dao.ArtistMessageDAO;
import com.example.fanmon_be.domain.chat.dao.UserMessageDAO;
import com.example.fanmon_be.domain.chat.entity.ArtistMessage;
import com.example.fanmon_be.domain.chat.entity.Chat;
import com.example.fanmon_be.domain.chat.entity.UserMessage;
import com.example.fanmon_be.domain.user.dao.UserDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.runtime.ObjectMethods;
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
    @Autowired
    ObjectMapper objectMapper;

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
    public UserMessage findById(UUID uuid){
        return userMessageDAO.findById(uuid).get();
    }

    // 아티스트 메세지
    public void saveAllArtistMessages(List<ArtistMessage> messages) {
        artistMessageDAO.saveAll(messages);
    }
    public List<ArtistMessage> findArtistMessagesByChatuuid(UUID chatuuid) {
        return artistMessageDAO.findByChatChatuuid(chatuuid);
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

        // 레디스 캐시에 저장된 메세지 불러오기
        List<Object> artistRedisMessages = redisTemplate.opsForList().range("ARTIST:" + chatUuid, 0, -1);
        List<Object> userRedisMessages = redisTemplate.opsForList().range("USER:" + chatUuid, 0, -1);

        List<Object> convertedRedisMessages = new ArrayList<>();
        if (artistRedisMessages != null) {
            for (Object message : artistRedisMessages) {
                try {
                    // Object를 JSON String으로 캐스팅하고, ArtistMessage 객체로 변환
                    String jsonMessage = objectMapper.writeValueAsString(message);
                    ArtistMessage msg = objectMapper.readValue(jsonMessage, ArtistMessage.class);
                    convertedRedisMessages.add(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (userRedisMessages != null) {
            for (Object message : userRedisMessages) {
                try {
                    // Object를 JSON String으로 캐스팅하고, UserMessage 객체로 변환
                    String jsonMessage = objectMapper.writeValueAsString(message);
                    UserMessage msg = objectMapper.readValue(jsonMessage, UserMessage.class);
                    convertedRedisMessages.add(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        allMessages.addAll(convertedRedisMessages);
        // 타임스탬프 기준으로 정렬 (내림차순)
        allMessages.sort((m1, m2) -> {
            LocalDateTime timestamp1 = getMessageTimestamp(m1);
            LocalDateTime timestamp2 = getMessageTimestamp(m2);
            return timestamp1.compareTo(timestamp2); // 내림차순 정렬
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

}
