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
    private String tip;
    private String info;
    private String history;

    private List<Map<String, Object>> restaurants;
    private Map<String, Object> video;
}
