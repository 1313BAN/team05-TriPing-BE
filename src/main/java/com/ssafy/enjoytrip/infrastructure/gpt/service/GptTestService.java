package com.ssafy.enjoytrip.infrastructure.gpt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptTestService {

    private final ChatClient chatClient;

    public String testGpt(String prompt) {
        try {
            var response = chatClient.prompt()
                    .user(prompt)
                    .call();
            return response.content();
        } catch (Exception e) {
            log.error("GPT 호출 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("GPT 호출에 실패했습니다.", e);
        }
    }
}