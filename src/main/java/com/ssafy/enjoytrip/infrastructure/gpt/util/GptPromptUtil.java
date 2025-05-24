package com.ssafy.enjoytrip.infrastructure.gpt.util;

import org.springframework.stereotype.Component;

@Component
public class GptPromptUtil {

    public String generateSystemPrompt() {
        return """
            너는 전문 여행 가이드 작가야. 아래 JSON 형식에 맞춰 응답해야 해.
            각 필드는 다음 목적에 따라 작성해:

            - "title": 관광지의 이름 (한글)
            - "info": 전체 개요 설명 (위치, 특징, 역사 포함)
            - "tip": 여행 팁 (형식: 리스트, 한 문장씩 여러 개)
            - "history": 간략한 역사 설명
            - "restaurants": 근처 맛집 2~3개 (각 항목에 name, description, distance 포함)
            - "video": 관광 소개 영상 (title, description, url 포함)

            아래와 같은 순수 JSON만 출력하고, JSON 이외의 텍스트는 절대 포함하지 마.
            JSON 응답 외에 어떤 말도 하지 마. 코드블럭이나 설명도 안 됨.
            """;
    }

    public String generateUserPrompt(String title, String address) {
        return String.format("""
            관광지 이름: %s
            주소: %s
            
            위 관광지에 대한 가이드를 반드시 JSON 형식으로 작성해주세요.JSON 응답 외에 어떤 말도 하지 마. 코드블럭이나 설명도 안 됨.
            """, title, address);
    }
}
