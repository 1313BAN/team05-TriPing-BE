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
public class GptSubGuideResponse {
    private String title;
    private String subtitle;

    private List<String> tip;
    private String info;
    private String history;


}