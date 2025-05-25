package com.ssafy.enjoytrip.infrastructure.gpt.service;

import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptGuideResponse;

public interface GptService {
    GptGuideResponse getGuideByTitleAndAddress(int id, String title, String address);

};


