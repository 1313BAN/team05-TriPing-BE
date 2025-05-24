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
    private List<String> tip; // ✅ 수정된 부분
    private String info;
    private String history;

    private List<Map<String, Object>> restaurants;
    private Map<String, Object> video;
}
