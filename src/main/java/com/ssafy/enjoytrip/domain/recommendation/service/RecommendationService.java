package com.ssafy.enjoytrip.domain.recommendation.service;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.RecommendedAttraction;

import java.math.BigDecimal;
import java.util.List;

public interface RecommendationService {
    List<RecommendedAttraction> recommendAttractionsForGpt(BigDecimal lat, BigDecimal lng, Long userId);
}
