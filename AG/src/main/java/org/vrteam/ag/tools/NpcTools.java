package org.vrteam.ag.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vrteam.ag.config.WebSocket;
import org.vrteam.ag.service.NpcChatService;
import org.vrteam.ag.webSocket.NPCWebSocketHandler;

import java.io.IOException;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/18 14:52
 * @description:
 */
@Component
public class NpcTools {

    @Autowired
    private NPCWebSocketHandler npcWebSocketHandler;
    @Tool("向指定目标移动")
    public void walkTo(
            @P("websocket_sessionID")String sessionId,
            @P("x坐标")double x,
            @P("y坐标")double y
    ) throws IOException {
        npcWebSocketHandler.sendToSession(sessionId,"Move("+x+y+")");

    }
}
