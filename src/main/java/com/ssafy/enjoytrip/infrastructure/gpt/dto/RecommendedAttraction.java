package com.ssafy.enjoytrip.infrastructure.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendedAttraction {
    private Long id;          // 관광지 ID
    private String title;     // 관광지 이름
    private String address;   // 주소
    private String reason;    // ✅ 추천 이유 (간단한 설명 2줄 이내)
    private int score;        // ✅ 추천도 (0~100%)

    private BigDecimal latitude;
    private BigDecimal longitude;

    String imageUrl;


}