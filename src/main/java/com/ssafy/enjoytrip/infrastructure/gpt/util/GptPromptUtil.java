package com.ssafy.enjoytrip.infrastructure.gpt.util;

import org.springframework.stereotype.Component;

@Component
public class GptPromptUtil {

    public String generatePromptText(String name, String address) {
        return """
            당신은 여행 가이드입니다. 아래 장소 정보를 바탕으로 여행자에게 도움이 되는 소개 문장을 작성해주세요.

            - 장소 이름: %s
            - 주소: %s

            다음 지침을 따라 작성해주세요:
            1. 소개는 2~3문장으로 간결하게 작성합니다.
            2. 장소의 매력을 생생하게 표현합니다.
            3. 현지인만 아는 팁, 추천 시간대, 인근 맛집/명소 정보 등을 포함하면 좋습니다.
            4. 실제 가이드북이나 블로그에 실릴 수 있는 유익한 정보 형식으로 작성해주세요.
            """.formatted(name, address);
    }
}
