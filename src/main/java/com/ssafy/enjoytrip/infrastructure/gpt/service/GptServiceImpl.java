package com.ssafy.enjoytrip.infrastructure.gpt.service;

import com.ssafy.enjoytrip.infrastructure.gpt.util.GptPromptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {

    private final ChatClient chatClient;
    private final GptPromptUtil gptPromptUtil;

    @Override
    public String getAnswer(String name, String address) {
        // ✨ 유틸 함수 호출로 프롬프트 문장 생성
        String promptText = gptPromptUtil.generatePromptText(name, address);

        return chatClient
                .prompt()
                .user(promptText)
                .call()
                .content();
    }
}
