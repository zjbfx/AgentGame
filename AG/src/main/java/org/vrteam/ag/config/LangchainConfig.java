package org.vrteam.ag.config;

import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/18 14:42
 * @description:
 */

@Configuration
public class LangchainConfig {
    @Autowired
    private OpenAiChatModel model;
    @Autowired
    private ChatMemoryStore redisChatMemoryStore;
    @Autowired
    private OpenAiEmbeddingModel embeddingModel;
    @Autowired
    private RedisEmbeddingStore redisEmbeddingStore;
    @Bean
    public ChatMemory chatMemory(){
        MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(20)
                .build();
        return memory;
    }
    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        ChatMemoryProvider chatMemoryProvider=new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(20)
                        .chatMemoryStore(redisChatMemoryStore)
                        .build();
                return memory;
            }
        };
        return chatMemoryProvider;

    }
    //构建向量数据库操作对象
    public EmbeddingStore store(){
        DocumentSplitter ds= DocumentSplitters.recursive(500,100);
        EmbeddingStoreIngestor ingestor=EmbeddingStoreIngestor.builder()
                .embeddingStore(redisEmbeddingStore)
                .documentSplitter(ds)
                .embeddingModel(embeddingModel)
                .build();
        //将文档分割并存入
        return redisEmbeddingStore;
    }
    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor(){
        DocumentSplitter ds= DocumentSplitters.recursive(500,100);
        EmbeddingStoreIngestor ingestor=EmbeddingStoreIngestor.builder()
                .embeddingStore(redisEmbeddingStore)
                .documentSplitter(ds)
                .embeddingModel(embeddingModel)
                .build();
        return ingestor;
    }

    //构建向量数据检索对象
    @Bean
    public ContentRetriever contentRetriever(){
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(redisEmbeddingStore)
                .minScore(0.5)
                .maxResults(3)
                .embeddingModel(embeddingModel)
                .build();
    }

}
