package com.ssafy.enjoytrip.infrastructure.gpt.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptRecommendResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptSubGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.RecommendedAttraction;
import com.ssafy.enjoytrip.util.RedisKeyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptServiceImpl implements GptService {

//    private final ChatClient chatClient;
//    private final GptPromptUtil gptPromptUtil;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final RedisTemplate<String, String> redisTemplate;
//
//
//    @Override
//    public GptGuideResponse getGuideByTitleAndAddress(int id, String title, String address) {
//
//        String key = RedisKeyUtil.buildGptGuideKey(id);
//        String fullPrompt = null;
//        String gptResponse = null;
//
//        try {
//            // ✅ Redis 캐시 조회
//            String cached = redisTemplate.opsForValue().get(key);
//            System.out.println(cached);
//            if (cached != null) {
//                log.info("📦 [GPT 캐시 HIT] id = {}", id);
//                GptGuideResponse cachedRes = objectMapper.readValue(cached, GptGuideResponse.class);
//                return cachedRes;
//            }
//
//            // ✅ 프롬프트 생성
//            String systemPrompt = gptPromptUtil.generateSystemPrompt();
//            String userPrompt = gptPromptUtil.generateUserPrompt(title, address);
//            fullPrompt = systemPrompt + "\n" + userPrompt;
//
//            log.debug("📤 생성된 프롬프트:\n{}", fullPrompt);
//
//            // ✅ GPT 호출
//            gptResponse = chatClient
//                    .prompt(new Prompt(fullPrompt))
//                    .call()
//                    .content();
//
//            log.debug("📥 GPT 응답 원문:\n{}", gptResponse);
//
//            // ✅ 응답 파싱 → DTO 생성
//            GptGuideResponse res = objectMapper.readValue(gptResponse, GptGuideResponse.class);
//            res.setTitle(title);
//            res.setAddress(address);
//            res.setVideo(null); // 수동 null 처리
//
//            // ✅ Redis 저장
//            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(res), Duration.ofDays(14));
//
//            log.info("🌐 [GPT 호출] id = {} → 캐시 저장 완료", id);
//
//            return res;
//
//        } catch (Exception e) {
//            log.error("❌ GPT 응답 파싱 실패");
//            log.error("🧾 요청 프롬프트:\n{}", fullPrompt);
//            log.error("📥 GPT 응답 (에러 발생 전 수신된 응답):\n{}", gptResponse);
//            log.error("📛 예외 메시지: {}", e.getMessage(), e);
//            throw new RuntimeException("GPT 응답 파싱 실패", e);
//        }
//    }
//
//
//    @Override
//    public GptSubGuideResponse getSubGuideByTitleAndSubTitle(String title, String subTitle) {
//
//        String key = RedisKeyUtil.buildGptSubGuideKey(title, subTitle);
//        String userPrompt = gptPromptUtil.generateSubPrompt(title, subTitle);
//        String gptSubResponse = null;
//
//        try {
//            // ✅ Redis 캐시 조회
//            String cached = redisTemplate.opsForValue().get(key);
//            if (cached != null) {
//                log.info("📦 [GPT SubGuide 캐시 HIT] title = {}, subTitle = {}", title, subTitle);
//                GptSubGuideResponse cachedRes = objectMapper.readValue(cached, GptSubGuideResponse.class);
//                return cachedRes;
//            }
//
//            // ✅ 프롬프트 로그 출력
//            log.debug("📤 생성된 프롬프트:\n{}", userPrompt);
//
//            // ✅ GPT 호출
//            gptSubResponse = chatClient
//                    .prompt(new Prompt(userPrompt))
//                    .call()
//                    .content();
//
//            log.debug("📥 GPT 응답 원문:\n{}", gptSubResponse);
//
//            // ✅ 응답 파싱 → DTO 생성
//            GptSubGuideResponse res = objectMapper.readValue(gptSubResponse, GptSubGuideResponse.class);
//            res.setTitle(title);
//            res.setSubtitle(subTitle);
//
//            // ✅ Redis 저장
//            redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(res), Duration.ofDays(14));
//            log.info("🌐 [GPT SubGuide 호출] {} - {} → 캐시 저장 완료", title, subTitle);
//
//            return res;
//
//        } catch (Exception e) {
//            log.error("❌ GPT SubGuide 응답 파싱 실패");
//            log.error("🧾 요청 프롬프트:\n{}", userPrompt);
//            log.error("📥 GPT 응답 (에러 발생 전 수신된 응답):\n{}", gptSubResponse);
//            log.error("📛 예외 메시지: {}", e.getMessage(), e);
//            throw new RuntimeException("GPT 응답 파싱 실패", e);
//        }
//    }
//
//    public GptRecommendResponse getRecommendFromGpt(String prompt) {
//        String gptResponse = null;
//
//        try {
//            // ✅ GPT 호출
//            gptResponse = chatClient
//                    .prompt(new Prompt(prompt))
//                    .call()
//                    .content();
//
//            log.debug("📥 GPT 추천 응답 원문:\n{}", gptResponse);
//
//            // ✅ 응답 파싱 → DTO
//            GptRecommendResponse res = objectMapper.readValue(gptResponse, GptRecommendResponse.class);
//            log.info("✅ GPT 추천 파싱 완료: {}개 추천됨", res.getRecommendations().size());
//            return res;
//
//        } catch (Exception e) {
//            log.error("❌ GPT 추천 응답 파싱 실패");
//            log.error("🧾 요청 프롬프트:\n{}", prompt);
//            log.error("📥 GPT 응답 (에러 발생 전 수신된 응답):\n{}", gptResponse);
//            log.error("📛 예외 메시지: {}", e.getMessage(), e);
//            throw new RuntimeException("GPT 추천 응답 파싱 실패", e);
//        }
//    }

}
