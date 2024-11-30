package com.example.webchatapp.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // brokerul de mesaje pentru rutele WebSocket
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // mesajele publice
        config.setApplicationDestinationPrefixes("/app"); // mesajele din aplicatie
    }

    // endpoint-ul la care se pot conecta clientii
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(
                            org.springframework.http.server.ServerHttpRequest request,
                            org.springframework.http.server.ServerHttpResponse response,
                            org.springframework.web.socket.WebSocketHandler wsHandler,
                            Map<String, Object> attributes) {
                        // Extrage sesiunea HTTP și asociază ID-ul acesteia cu atributele WebSocket
                        if (request instanceof org.springframework.http.server.ServletServerHttpRequest servletRequest) {
                            HttpSession session = servletRequest.getServletRequest().getSession(false);
                            if (session != null) {
                                attributes.put("HTTP_SESSION_ID", session.getId());
                            }
                        }
                        return true;
                    }

                    @Override
                    public void afterHandshake(
                            org.springframework.http.server.ServerHttpRequest request,
                            org.springframework.http.server.ServerHttpResponse response,
                            org.springframework.web.socket.WebSocketHandler wsHandler,
                            Exception exception) {
                        // Nimic de făcut aici momentan
                    }
                })
                .withSockJS(); // Activează SockJS pentru fallback
    }
}
