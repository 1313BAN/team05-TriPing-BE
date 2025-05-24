package com.ssafy.enjoytrip.domain.recommendation.service;

public class RecommendationServiceImpl implements RecommendationService {
    // 추천 서비스 주요 로직 구현
    // AttractionService.findNearbyForRecommendation 호출 -> 주변 관광지 15개
    // VisitLogService에서 사용자의 방문기록 호출 -> 방문기록 N개
    // GptService에서 프롬포트 보내는 부분 호출
    // dto로 정제해서 리턴
}
