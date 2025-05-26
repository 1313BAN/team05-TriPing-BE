package com.ssafy.enjoytrip.domain.visitlog.service;

import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreateRequest;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreatedResponse;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogListDTO;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogsForRecommendDTO;

public interface VisitLogService {
    VisitLogCreatedResponse createVisitLog(Long userId, VisitLogCreateRequest request);
    void updatePreference(Long userId, Long visitLogId, Integer preference);
    VisitLogsForRecommendDTO getVisitLogsByUser(Long userId, Integer page, Integer size);
    VisitLogListDTO getVisitLogsByUserGroupedByDate(Long userId, Integer page);
}