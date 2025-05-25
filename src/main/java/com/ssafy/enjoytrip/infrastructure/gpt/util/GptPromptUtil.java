package com.ssafy.enjoytrip.infrastructure.gpt.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class GptPromptUtil {

    @Value("${GUIDE_PROMPT_PATH}")
    private String guidePromptPath;

    @Value("${SYSTEM_PROMPT_PATH}")
    private String systemPromptPath;

    private String guidePromptTemplate;
    private String systemPromptTemplate;

    @PostConstruct
    public void loadPrompts() {
        this.guidePromptTemplate = loadPromptFromFile(guidePromptPath);
        this.systemPromptTemplate = loadPromptFromFile(systemPromptPath);
    }

    private String loadPromptFromFile(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ 프롬프트 파일 읽기 실패: " + path, e);
        }
    }

    public String generateUserPrompt(String title, String address) {
        return String.format(guidePromptTemplate, title, address);
    }

    public String generateSystemPrompt() {
        return systemPromptTemplate;
    }
}
