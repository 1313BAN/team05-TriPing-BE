package com.ssafy.enjoytrip.infrastructure.gpt;

import com.ssafy.enjoytrip.infrastructure.gpt.service.GptServiceImpl;
import com.ssafy.enjoytrip.infrastructure.gpt.util.GptPromptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GptServiceTest {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Test
    void gpt_responseTest() {
        // 1. OpenAI API와 모델 설정
        OpenAiApi openAiApi = new OpenAiApi(apiKey);
        ChatModel chatModel = new OpenAiChatModel(openAiApi);

        // 2. ChatClient 생성
        ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultSystem("너는 친절한 한국어 여행 가이드야.")
                .build();

        // 3. GptPromptUtil 생성
        GptPromptUtil promptUtil = new GptPromptUtil();

        // 4. GptServiceImpl 생성
        GptServiceImpl gptService = new GptServiceImpl(chatClient, promptUtil);

        // 5. 장소 정보 테스트
        String name = "경복궁";
        String address = "서울특별시 종로구 사직로 161";

        String answer = gptService.getAnswer(name, address);

        // 6. 결과 출력 및 검증
        System.out.println("🧠 GPT 응답: " + answer);
        assertThat(answer).isNotBlank();
    }
}
