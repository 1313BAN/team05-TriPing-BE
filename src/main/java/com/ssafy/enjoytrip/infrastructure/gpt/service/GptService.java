package com.ssafy.enjoytrip.infrastructure.gpt.service;

import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptSubGuideResponse;

public interface GptService {
    GptGuideResponse getGuideByTitleAndAddress(int id, String title, String address);
    GptSubGuideResponse getSubGuideByTitleAndSubTitle(String title, String subTitle);
};


