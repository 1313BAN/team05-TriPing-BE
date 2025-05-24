package com.ssafy.enjoytrip.domain.recommendation.controller;

import com.ssafy.enjoytrip.auth.jwt.UserPrincipal;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionForRecommendDTO;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import com.ssafy.enjoytrip.domain.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

//    private final RecommendationService recommendationService;
    private final AttractionService attractionService;

    // AI 관광지 추천
    @GetMapping("/attractions")
    public List<AttractionForRecommendDTO> recommendAttractions(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @AuthenticationPrincipal UserPrincipal userPrincipal

    ) {
        // 테스트용 (여기서 호출 x)
        return attractionService.findNearbyForRecommendation(lat, lng);

        // 서비스단 호출하고 responseEntity로 RecommendationDTO 반환
    }
}
