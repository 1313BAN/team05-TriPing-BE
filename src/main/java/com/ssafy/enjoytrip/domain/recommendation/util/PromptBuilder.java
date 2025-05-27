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
                                - 체류 시간이 길고 선호도가 높은 장소는 사용자가 특히 좋아했던 장소로 간주하며, 이를 기반으로 유사한 장소를 찾는 것이 중요합니다.
                                - 추천 대상에서 이미 방문한 장소는 제외해야 합니다.
                                - 추천도는 85 이상으로만 추천해줘
                                - 응답할 때는 추천도가 높은 순서대로 정렬해 주세요.
                                - 사용자의 방문 기록 중에서 특히 **오래 머물렀고 선호도가 높았던 관광지**를 우선적으로 분석한 뒤, 그와 **유사한 분위기나 특징을 가진 관광지를 골라 추천**하세요.
                                  예: "전통문화", "역사", "사진명소", "자연경관", "도심산책", "전시·체험 공간" 등으로 유형을 분류한 뒤, 성향이 비슷한 장소를 선택합니다.
                
                                응답 조건:
                                - JSON 형식으로 꼭 3개만 추천
                                - 각 항목에는 다음 필드를 포함해야 합니다:
                                  - id: 추천 관광지 ID
                                  - title: 관광지 이름
                                  - address: 주소
                                  - reason: 추천 이유 \s
                                    (추천 이유는 단순한 장소 소개가 아니라, **사용자의 과거 선호와 연결 지어 공감 가는 방식으로 설명**해 주세요. \s
                                    말투는 마치 현장에서 친절히 안내해주는 **전문 여행 가이드처럼 자연스럽고 설득력 있게** 구성해야 합니다.)
                
                                    예시:
                                    - "예전에 전통문화 공간을 여유롭게 둘러보신 걸 보면 이런 역사적인 장소도 분명 마음에 드실 거예요."
                                    - "조용한 도심 산책길을 좋아하시는 편이라, 이 거리도 편하게 둘러보시기 좋을 것 같아요."
                                  - score: 추천도 (0~100%, int)
                
                                응답 예시:
                                {
                                  "recommendations": [
                                    {
                                      "id": 64074,
                                      "title": "경주 계림",
                                      "address": "경상북도 경주시 교동",
                                      "reason": "자연 속에서 여유롭게 머무셨던 기록이 있어요. 이 고즈넉한 숲길도 분명히 좋아하실 거예요.",
                                      "score": 95
                                    },
                                    {
                                      "id": 64167,
                                      "title": "경주향교",
                                      "address": "경상북도 경주시 교촌안길 27-20 (교동)",
                                      "reason": "전통적인 분위기의 공간을 오래 머무르며 선호하셨던 걸 보면, 이 고택도 마음에 드실 것 같아요.",
                                      "score": 91
                                    },
                                    {
                                      "id": 64101,
                                      "title": "경주 동부 사적지대",
                                      "address": "경상북도 경주시 첨성로 157",
                                      "reason": "역사 유적지에 관심이 많으신 것 같아 추천드려요. 조용히 걸으며 감상하시기 좋아요.",
                                      "score": 87
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
