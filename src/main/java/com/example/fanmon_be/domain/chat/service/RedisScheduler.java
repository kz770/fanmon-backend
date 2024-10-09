package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.entity.ArtistMessage;
import com.example.fanmon_be.domain.chat.entity.UserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RedisScheduler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String USER_TYPE = "USER";
    private static final String ARTIST_TYPE = "ARTIST";
    @Scheduled(fixedRate = 60000)  // 1분 간격으로 실행
    public void saveMessages(){
        System.out.println("redis scheduler 실행 : 모든 메세지가 저장됩니다!");
        List<ArtistMessage> allArtistMessagesToSave = new ArrayList<>(); // 모든 메시지를 저장할 리스트
        // 아티스트 저장 메서드
        Set<String> keys=redisTemplate.keys(ARTIST_TYPE+"*"); //모든 아티스트에 대하여 저장
        if (keys != null) {
            for (String key : keys) {
                List<Object> redisMessages = redisTemplate.opsForList().range(key, 0, -1);
                if (redisMessages != null && !redisMessages.isEmpty()) {
                    List<ArtistMessage> messages = redisMessages.stream()
                            .map(redisMessage -> objectMapper.convertValue(redisMessage, ArtistMessage.class))
                            .collect(Collectors.toList());

                    allArtistMessagesToSave.addAll(messages); // 모든 메시지 추가
                    redisTemplate.delete(key); // Redis에서 메시지 삭제
                }
            }
        }
        if (!allArtistMessagesToSave.isEmpty()){
            messageService.saveAllArtistMessages(allArtistMessagesToSave);
        }
        System.out.println(allArtistMessagesToSave);

        // 유저 메세지 DB 저장
        List<UserMessage> allUserMessagesToSave = new ArrayList<>(); // 모든 메시지를 저장할 리스트
        keys = redisTemplate.keys(USER_TYPE+ "*");
        if (keys != null){
            for (String key : keys) {
                List<Object> redisMessages = redisTemplate.opsForList().range(key, 0, -1);
                if (redisMessages != null && !redisMessages.isEmpty()) {
                    List<UserMessage> messages = redisMessages.stream()
                            .map(redisMessage -> objectMapper.convertValue(redisMessage, UserMessage.class))
                            .collect(Collectors.toList());

                    allUserMessagesToSave.addAll(messages); // 모든 메시지 추가
                    redisTemplate.delete(key); // Redis에서 메시지 삭제
                }
            }
        }
        if (!allUserMessagesToSave.isEmpty()){
            messageService.saveAllUserMessages(allUserMessagesToSave);
        }
        System.out.println(allUserMessagesToSave);

    }
}
