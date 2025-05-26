package com.ssafy.enjoytrip.domain.recommendation.controller;

import com.ssafy.enjoytrip.auth.jwt.UserPrincipal;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionForRecommendDTO;
import com.ssafy.enjoytrip.domain.attraction.dto.RecommendationDTO;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import com.ssafy.enjoytrip.domain.recommendation.service.RecommendationService;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptRecommendResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.RecommendedAttraction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/attractions")
    public List<RecommendedAttraction> recommendAttractions(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

//        List<RecommendationDTO> result =
//                recommendationService.recommendAttractionsForGpt(lat, lng, userPrincipal.getId());

        return recommendationService.recommendAttractionsForGpt(lat, lng, userPrincipal.getId());
    }





}
