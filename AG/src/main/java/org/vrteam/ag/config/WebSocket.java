package org.vrteam.ag.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.vrteam.ag.webSocket.NPCWebSocketHandler;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/17 20:14
 * @description:
 */
@Configuration
@EnableWebSocket
public class WebSocket implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(npcWebSocketHandler(), "/ws/npc")
                .setAllowedOrigins("*");
    }
    @Bean
    public WebSocketHandler npcWebSocketHandler() {
        return new NPCWebSocketHandler();
    }
}

