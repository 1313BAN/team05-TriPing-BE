package com.ssafy.enjoytrip.domain.visitlog.service;

import com.ssafy.enjoytrip.domain.attraction.mapper.AttractionMapper;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreateRequest;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreatedResponse;
import com.ssafy.enjoytrip.domain.visitlog.exception.VisitLogException;
import com.ssafy.enjoytrip.domain.visitlog.mapper.VisitLogMapper;
import com.ssafy.enjoytrip.domain.visitlog.model.VisitLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ssafy.enjoytrip.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogMapper visitLogMapper;
    private final AttractionService attractionService;

    @Override
    @Transactional
    public VisitLogCreatedResponse createVisitLog(Long userId, VisitLogCreateRequest request) {
        if (request.getExitedAt().isBefore(request.getEnteredAt())) {
            throw new VisitLogException(INVALID_TIME);
        }

        VisitLog log = VisitLog.builder()
                .userId(userId)
                .attractionNo(request.getAttractionNo())
                .enteredAt(request.getEnteredAt())
                .exitedAt(request.getExitedAt())
                .preference(request.getPreference())
                .build();

        visitLogMapper.insertVisitLog(log);
        attractionService.increaseVisitCount(request.getAttractionNo());
        return new VisitLogCreatedResponse(log.getId());
    }

    @Override
    @Transactional
    public void updatePreference(Long userId, Long visitLogId, Integer preference) {
        if (!visitLogMapper.existsByIdAndUserId(visitLogId, userId)) {
            throw new VisitLogException(VISITLOG_NOT_FOUND);
        }
        if (preference == null || preference < 1 || preference > 10) {
            throw new VisitLogException(INVALID_PREFERENCE);
        }
        visitLogMapper.updatePreference(userId, visitLogId, preference);
    }
}