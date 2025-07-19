package org.vrteam.ag.webSocket;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/17 20:18
 * @description:
 */

@Slf4j
@Component
public class NPCWebSocketHandler extends TextWebSocketHandler {
    public static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        log.info("连接成功，sessionID："+session.getId());
        sendMessage(session,"SESSION_ID:"+session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 处理来自UE的消息
        String payload = message.getPayload();
        // 解析并处理消息...
        log.info("接收到信息:\n"+message.getPayload());


        // 示例：发送移动确认
        sendMessage(session, "{\"messageType\":\"MovementAck\",\"payload\":\"{}\"}");

    }

    public void sendMessage(WebSocketSession session, String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            // 错误处理
        }
    }
    public void sendToSession(String sessionId, String message) throws IOException {
        sessions.stream()
                .filter(s -> s.getId().equals(sessionId) && s.isOpen())
                .findFirst()
                .ifPresent(session -> {
                    try {
                        session.sendMessage(new TextMessage(message));
                    } catch (IOException e) {
                        // 处理异常

                    }
                });
    }

}
