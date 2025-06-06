package com.ssafy.enjoytrip.infrastructure.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GptRecommendResponse {
    private List<RecommendedAttraction> recommendations;
}
