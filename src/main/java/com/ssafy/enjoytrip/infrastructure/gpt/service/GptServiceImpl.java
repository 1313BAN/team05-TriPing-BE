package com.ssafy.enjoytrip.infrastructure.gpt.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.util.GptPromptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.prompt.Prompt;


import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {

    private final ChatClient chatClient;
    private final GptPromptUtil gptPromptUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public GptGuideResponse getGuideByTitleAndAddress(String title, String address) {
        String systemPrompt = gptPromptUtil.generateSystemPrompt();
        String userPrompt = gptPromptUtil.generateUserPrompt(title, address);

        try {
            // 💬 system + user 프롬프트 구성
            Prompt prompt = Prompt.builder()
                    .addMessage("system", systemPrompt)
                    .addMessage("user", userPrompt)
                    .build();

            // 🧠 GPT 호출
            String gptResponse = chatClient
                    .prompt()
                    .prompt(prompt)
                    .call()
                    .content();

            // 📦 JSON → Map 변환
            Map<String, Object> map = objectMapper.readValue(gptResponse, new TypeReference<>() {});

            // ✅ DTO 생성 및 반환
            return GptGuideResponse.builder()
                    .title((String) map.get("title"))
                    .tip((String) map.get("tip"))
                    .info((String) map.get("info"))
                    .history((String) map.get("history"))
                    .restaurants((List<Map<String, Object>>) map.get("restaurants"))
                    .video((Map<String, Object>) map.get("video"))
                    .build();

        } catch (Exception e) {
            log.error("❌ GPT 응답 파싱 실패", e);
            throw new RuntimeException("GPT 응답 파싱 실패", e);
        }
    }

}
