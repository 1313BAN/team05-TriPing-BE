package com.ssafy.enjoytrip.domain.recommendation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionForRecommendDTO;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogDTO;

import java.time.Duration;
import java.util.List;

/**
 * JSON 직렬화 및 GPT용 문자열 포맷 유틸리티
 */
public class SerializationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("📛 JSON 직렬화 실패", e);
        }
    }

    /**
     * 주변 관광지 리스트를 요약된 문자열로 포맷팅
     */
    public static String formatAttractionSummary(List<AttractionForRecommendDTO> attractions) {
        StringBuilder sb = new StringBuilder("🗺️ 주변 추천 관광지 목록:\n");
        for (AttractionForRecommendDTO dto : attractions) {
            sb.append("관광지 ")
                    .append(dto.getNo()).append("번 - [").append(dto.getTitle()).append("], ")
                    .append("주소: ").append(dto.getAddress()).append("\n");
        }
        return sb.toString();
    }

    /**
     * 사용자 방문 기록 리스트를 요약된 문자열로 포맷팅 (체류시간, 선호도 포함)
     */
    public static String formatVisitLogSummary(List<VisitLogDTO> logs) {
        StringBuilder sb = new StringBuilder("🧾 사용자 방문 기록 목록:\n");
        for (VisitLogDTO dto : logs) {
            String enteredAtStr = dto.getEnteredAt() != null ? dto.getEnteredAt().toString() : "알 수 없음";
            String exitedAtStr = dto.getExitedAt() != null ? dto.getExitedAt().toString() : "알 수 없음";

            long durationMinutes = -1;
            if (dto.getEnteredAt() != null && dto.getExitedAt() != null) {
                durationMinutes = Duration.between(dto.getEnteredAt(), dto.getExitedAt()).toMinutes();
            }

            String preferenceStr = dto.getPreference() != null ? dto.getPreference().toString() : "없음";

            sb.append("관광지 ")
                    .append(dto.getAttractionNo()).append("번 - [").append(dto.getTitle()).append("], ")
                    .append("입장: ").append(enteredAtStr).append(", ")
                    .append("퇴장: ").append(exitedAtStr).append(", ")
                    .append("체류시간: ").append(durationMinutes >= 0 ? durationMinutes + "분" : "계산 불가").append(", ")
                    .append("선호도: ").append(preferenceStr)
                    .append("\n");
        }
        return sb.toString();
    }
}
