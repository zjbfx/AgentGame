package org.vrteam.ag.controller;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vrteam.ag.result.Result;
import org.vrteam.ag.service.NpcChatService;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * @title：
 * @author: Jeffery
 * @date: 2025/7/18 14:47
 * @description:
 */
@RestController
public class NpcChatController {
    @Autowired
    private NpcChatService npcChatService;
    @Autowired
    EmbeddingStoreIngestor embeddingStoreIngestor;
    @RequestMapping(value = "/chat",produces = "text/html;charset=utf-8")
    public Flux<String> chat(String memoryId,String message){
        return npcChatService.chat(memoryId,message);
    }
    @PostMapping("/environment")
    public Result envScan(@RequestBody String env){


        try {
            Map<String, String> metadata = new HashMap<>();
            Document document = Document.from(env);
            embeddingStoreIngestor.ingest(document);
            return Result.success("环境数据已成功存入向量数据库");
        } catch (Exception e) {
            return Result.error( "存入向量数据库失败: " + e.getMessage());
        }
    }
}
