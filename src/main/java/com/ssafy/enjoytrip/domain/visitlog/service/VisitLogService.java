package com.ssafy.enjoytrip.domain.visitlog.service;

import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreateRequest;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreatedResponse;

public interface VisitLogService {
    VisitLogCreatedResponse createVisitLog(Long userId, VisitLogCreateRequest request);
    void updatePreference(Long userId, Long visitLogId, Integer preference);
}