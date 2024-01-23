package com.teamc.moodtracker.config.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://192.168.0.214:8081", "http://localhost:8081", "http://192.168.0.43:8081") // CORS 허용
                .withSockJS(); // SocketJS 연결 설정
    }

    // STOMP에서 사용하는 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // vue에서 메시지를 보내는 주소
        config.setApplicationDestinationPrefixes("/pub");

        // 서버가 메세지를 보내는 주소
        config.enableSimpleBroker("/topic", "/queue");
    }
}
