package com.ssafy.enjoytrip.domain.recommendation.service;

import com.ssafy.enjoytrip.domain.attraction.dto.AttractionForRecommendDTO;
import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import com.ssafy.enjoytrip.domain.recommendation.util.PromptBuilder;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogDTO;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogsForRecommendDTO;
import com.ssafy.enjoytrip.domain.visitlog.service.VisitLogService;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptRecommendResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.RecommendedAttraction;
//import com.ssafy.enjoytrip.infrastructure.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final AttractionService attractionService;
    private final VisitLogService visitLogService;
//    private final GptService gptService;

    @Override
    public List<RecommendedAttraction> recommendAttractionsForGpt(BigDecimal lat, BigDecimal lng, Long userId) {
        //  1단계: 주변 관광지 15개 받아오기
        List<AttractionForRecommendDTO> nearbyAttractions =
                attractionService.findNearbyForRecommendation(lat, lng);

        //  2단계: 사용자 방문기록 받아오기
        VisitLogsForRecommendDTO visitLogsWrapper =
                visitLogService.getVisitLogsByUser(userId, 1, 10);
        List<VisitLogDTO> visitLogs = visitLogsWrapper.getVisitLogs();

        //  3단계: 프롬프트 생성
        String prompt = PromptBuilder.buildRecommendationPrompt(nearbyAttractions, visitLogs);
        System.out.println("📤 생성된 프롬프트:\n" + prompt);

        //  4단계: GPT 호출 및 응답 파싱
//        GptRecommendResponse response = gptService.getRecommendFromGpt(prompt);
//
//        List<RecommendedAttraction> list = new ArrayList<>();
//        for (RecommendedAttraction rec : response.getRecommendations()) {
//            // 기존 nearbyAttractions에서 imageUrl 매칭
//            nearbyAttractions.stream()
//                    .filter(dto -> dto.getNo().equals(rec.getId()))
//                    .findFirst()
//                    .ifPresent(matched -> {
//                        rec.setImageUrl(matched.getImageUrl());
//                    });
//
//            // 위도, 경도 매칭 (AttractionService 통해 DB 조회)
//            try {
//                Attraction attraction = attractionService.getAttraction(rec.getId().intValue());
//                rec.setLatitude(attraction.getLatitude());
//                rec.setLongitude(attraction.getLongitude());
//            } catch (Exception e) {
//                System.out.printf("❗ 위경도 조회 실패: id = %d (%s)\n", rec.getId(), e.getMessage());
//            }
//            list.add(rec);
//        }
//        return list;
        return null;
    }
}
