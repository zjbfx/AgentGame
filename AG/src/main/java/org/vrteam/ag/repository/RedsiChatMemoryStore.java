package org.vrteam.ag.repository;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/18 15:30
 * @description:
 */
@Repository
public class RedsiChatMemoryStore implements ChatMemoryStore {
    //注入RedisTemplate
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        //1.redis->data(json)
        String json= redisTemplate.opsForValue().get(memoryId);
        //2.json->list

        return ChatMessageDeserializer.messagesFromJson(json);
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        //更新会话消息
        //1.List->json
        String json= ChatMessageSerializer.messagesToJson(list);
        //2.data->json
        redisTemplate.opsForValue().set(memoryId.toString(),json, Duration.ofDays(10));
    }

    @Override
    public void deleteMessages(Object memoryId) {

    }
}