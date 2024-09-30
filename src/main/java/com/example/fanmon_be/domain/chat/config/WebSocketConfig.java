package com.example.fanmon_be.domain.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    // web socket의 메세지 라우팅을 위한 목적지 설정
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 구독을 위한 브로커 경로
        // 이 경로를 구독하여 서버에서 발행된 메세지 수신 가능
        // 서버가 클라이언트에게 메세지를 발송할 때 사용
        config.enableSimpleBroker("/topic");
        // 클라이언트가 이 경로로 메세지를 보내면 해당 메세지는 지정된 컨트롤러 메서드로 자동 라우팅
        config.setApplicationDestinationPrefixes("/app");

    }

    @Override
    // stomp 프로토콜을 사용할 때 websocket 연결을 위한 엔드포인트를 등록하는 역할
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 websocket 서버에 연결하기 위해 사용할 수 있는 url을 정의한다
        // 해당 endpoint를 통해 websocket 세션을 시작한다
        registry.addEndpoint("/chat")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
        // socketjs 를 사용하면 브라우저가 websocket을 지원하지 않을 경우 대체 프로토콜(ex ajax polling)을 통해 연결
        // 호환성 향상
    }

}
