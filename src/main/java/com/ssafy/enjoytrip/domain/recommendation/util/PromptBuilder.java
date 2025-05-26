package com.ssafy.enjoytrip.domain.recommendation.util;

import com.ssafy.enjoytrip.domain.attraction.dto.AttractionForRecommendDTO;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogDTO;

import java.util.List;

public class PromptBuilder {

    /**
     * GPT에게 보낼 최종 프롬프트 문자열 생성
     */
    public static String buildRecommendationPrompt(
            List<AttractionForRecommendDTO> attractions,
            List<VisitLogDTO> visitLogs) {

        StringBuilder prompt = new StringBuilder();

        // ✅ GPT 역할 및 조건 지시 (추천 이유 + 추천도 포함)
        prompt.append("""
                당신은 사용자의 취향과 여행 이력을 기반으로 관광지를 추천해주는 AI 가이드입니다.
                아래에는 사용자의 '방문 기록'과 현재 위치 주변의 '관광지 목록'이 함께 제공됩니다.
                
                절대로 응답에 백틱(`), 마크다운 형식, ```json 등을 포함하지 마세요. JSON 본문만 반환하세요.
                

                당신의 목표는 다음과 같습니다:
                - 반드시 '주변 관광지 목록' 안에서만 3곳을 골라 추천해야 합니다.
                - '사용자 방문 기록'은 그 사람이 어떤 장소를 얼마나 오래, 얼마나 만족하며 있었는지를 알려줍니다.
                - 체류시간이 길고 선호도가 높은 장소는 유의미한 선호라고 판단해 반영해 주세요.
                - 추천 대상에서 이미 방문한 장소는 제외해야 합니다.
                - 응답할때 추천도가 높은 순서대로 응답해줘.

                응답 조건:
                - JSON 형식으로 꼭 3개만 추천
                - 각 항목에는 다음 필드를 포함해야 합니다:
                  - id: 추천 관광지 ID
                  - title: 관광지 이름
                  - address: 주소
                  - reason: 추천 이유 (간단하게 2줄 이내로,사용자의 행동 데이터를 기반하고 장소에 대한 정보를 종합으로 판단)
                  - score: 추천도 (0~100%, int)

                응답 예시:
                {
                  "recommendations": [
                    {
                      "id": 123,
                      "title": "경복궁",
                      "address": "서울 종로구 사직로 161",
                      "reason": "조선 시대 궁궐로 역사적 가치가 높고, 사진 찍기 좋은 장소입니다.",
                      "score": 5
                    },
                    {
                      "id": 456,
                      "title": "창덕궁",
                      "address": "서울 종로구 율곡로 99",
                      "reason": "고궁과 자연이 조화롭게 어우러져 있어 산책에 좋아요.",
                      "score": 4
                    },
                    {
                      "id": 789,
                      "title": "북촌한옥마을",
                      "address": "서울 종로구 계동길",
                      "reason": "전통적인 한옥과 현대적인 감성이 잘 어우러진 동네입니다.",
                      "score": 4
                    }
                  ]
                }
                """);

        // ✅ 관광지 목록 삽입
        prompt.append("\n").append(SerializationUtil.formatAttractionSummary(attractions)).append("\n");

        // ✅ 사용자 방문 기록 삽입
        prompt.append(SerializationUtil.formatVisitLogSummary(visitLogs)).append("\n");

        return prompt.toString();
    }
}
