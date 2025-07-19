package org.vrteam.ag.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/18 14:48
 * @description:
 */
@AiService (
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        streamingChatModel = "openAiStreamingChatModel",
        //chatMemory = "chatMemory",//配置会话记忆对象
        chatMemoryProvider = "chatMemoryProvider",//配置会话对象提供者对象
        contentRetriever="contentRetriever",//配置向量数据库检索对象
        tools="npcTools"
)
public interface NpcChatService {
    @SystemMessage(fromResource = "system.txt")//设置系统消息
    //@UserMessage("{{it}}")//拼接用户的消息
    public Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
}
