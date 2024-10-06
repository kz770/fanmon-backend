package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.entity.Message;
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
//        System.out.println("redis scheduler 실행");
        List<Message> allMessagesToSave = new ArrayList<>(); // 모든 메시지를 저장할 리스트
        Set<String> keys=redisTemplate.keys(ARTIST_TYPE+"*"); //모든 아티스트에 대하여 저장
        if (keys != null){
            for(String key:keys){
                List<Object> redisMessages = redisTemplate.opsForList().range(key, 0, -1);
                System.out.println(redisMessages);
                if (redisMessages != null && !redisMessages.isEmpty()) {
                    List<Message> messages = redisMessages.stream()
                            .map(redisMessage -> objectMapper.convertValue(redisMessage, Message.class))
                            .collect(Collectors.toList());
                    allMessagesToSave.addAll(messages);
                    redisTemplate.delete(key);
                }
            }
        }
        if (!allMessagesToSave.isEmpty()){
//            messageService.saveAll(allMessagesToSave);
        }
        System.out.println(allMessagesToSave);

//        // 유저 메세지 DB 저장
//        keys = redisTemplate.keys(USER_TYPE+ "*");
//        if (keys != null){
//            for (String key : keys){
//                List<Message> messages = redisTemplate.opsForList().range(key, 0, -1);
//                if (messages != null && !messages.isEmpty()) {
//                    allMessagesToSave.addAll(messages);
//                    redisTemplate.delete(key);
//                }
//            }
//        }
//        if (!allMessagesToSave.isEmpty()){
//            messageService.saveAll(allMessagesToSave);
//        }
    }
}
