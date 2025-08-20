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

//    @Value("${GUIDE_PROMPT_PATH}")
//    private String guidePromptPath;
//
//    @Value("${SYSTEM_PROMPT_PATH}")
//    private String systemPromptPath;
//
//    @Value("${SUB_GUIDE_PROMPT_PATH}")
//    private String subGuidePromptPath;
//
//    private String guidePromptTemplate;
//    private String systemPromptTemplate;
//    private String subGuidePromptTemplate;
//
//
//    @PostConstruct
//    public void loadPrompts() {
//        this.guidePromptTemplate = loadPromptFromFile(guidePromptPath);
//        this.systemPromptTemplate = loadPromptFromFile(systemPromptPath);
//        this.subGuidePromptTemplate = loadPromptFromFile(subGuidePromptPath); // ✅ 서브용 템플릿 로드
//    }
//
//    private String loadPromptFromFile(String path) {
//        try {
//            ClassPathResource resource = new ClassPathResource(path);
//            try (BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
//                return reader.lines().collect(Collectors.joining("\n"));
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("❌ 프롬프트 파일 읽기 실패: " + path, e);
//        }
//    }
//
//    // 일반 가이드
//    public String generateUserPrompt(String title, String address) {
//        return String.format(guidePromptTemplate, title, address);
//    }
//
//    public String generateSystemPrompt() {
//        return systemPromptTemplate;
//    }
//
//    // 서브 관광지용 사용자 프롬프트 생성
//    public String generateSubPrompt(String title, String subTitle) {
//        return String.format(subGuidePromptTemplate, title, subTitle);
//    }
}
