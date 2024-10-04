package com.example.fanmon_be.domain.chat.service;

import com.example.fanmon_be.domain.chat.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class MessagePublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;
    private final ObjectMapper objectMapper; // ObjectMapper 추가

    public void publish(Message message){
        try {
            String jsonMessage = objectMapper.writeValueAsString(message); // 객체를 JSON 문자열로 변환
            redisTemplate.convertAndSend(topic.getTopic(), jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 에러 처리
        }
    }
}
