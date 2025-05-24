package com.ssafy.enjoytrip.infrastructure.gpt.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.util.GptPromptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

import org.springframework.stereotype.Service;

import java.util.HashMap;
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

        String fullPrompt = null;
        String gptResponse = null;

        try {

            // 프롬프트 생성
            String systemPrompt = gptPromptUtil.generateSystemPrompt();
            String userPrompt = gptPromptUtil.generateUserPrompt(title, address);
            fullPrompt = systemPrompt + "\n" + userPrompt;

            log.debug("📤 생성된 프롬프트:\n{}", fullPrompt);

            // Prompt 객체 생성
            Prompt prompt = new Prompt(fullPrompt);

            // GPT 호출
            gptResponse = chatClient
                    .prompt(prompt)
                    .call()
                    .content();

            log.debug("📥 GPT 응답 원문:\n{}", gptResponse);

            // JSON → Map 파싱
            Map<String, Object> map = objectMapper.readValue(gptResponse, new TypeReference<>() {});

            // DTO 생성
            return objectMapper.readValue(gptResponse, GptGuideResponse.class);


        } catch (Exception e) {
            log.error("❌ GPT 응답 파싱 실패");
            log.error("🧾 요청 프롬프트:\n{}", fullPrompt);
            log.error("📥 GPT 응답 (에러 발생 전 수신된 응답):\n{}", gptResponse);
            log.error("📛 예외 메시지: {}", e.getMessage(), e);
            throw new RuntimeException("GPT 응답 파싱 실패", e);
        }
    }


}
