package com.ssafy.enjoytrip.infrastructure.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GptGuideResponse {
    private String title;
    private List<String> tip;
    private String info;
    private String history;
    private String address;

    private List<String> description;       // ✅ 기본 정보 (설명)
    private List<String> operationInfo;     // ✅ 운영 정보
    private List<String> contactInfo;       // ✅ 문의처

    private List<Map<String, Object>> restaurants;
    private Map<String, Object> video;

    private List<String> photoSpots;        // ✅ 포토 스팟
    private List<String> amenities;         // ✅ 관광지 편의시설
}
