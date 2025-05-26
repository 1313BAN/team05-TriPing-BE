package com.ssafy.enjoytrip.infrastructure.gpt.service;

import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptRecommendResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptSubGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.RecommendedAttraction;

import java.util.List;

public interface GptService {
    GptGuideResponse getGuideByTitleAndAddress(int id, String title, String address);
    GptSubGuideResponse getSubGuideByTitleAndSubTitle(String title, String subTitle);
    GptRecommendResponse getRecommendFromGpt(String prompt);

};


