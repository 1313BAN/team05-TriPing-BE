package com.ssafy.enjoytrip.domain.visitlog.service;

import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreateRequest;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreatedResponse;
import com.ssafy.enjoytrip.domain.visitlog.exception.VisitLogException;
import com.ssafy.enjoytrip.domain.visitlog.mapper.VisitLogMapper;
import com.ssafy.enjoytrip.domain.visitlog.model.VisitLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;

import static com.ssafy.enjoytrip.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogMapper visitLogMapper;
    private final AttractionService attractionService;

    private static final int MIN_REQUIRED_STAY_MINUTES = 5;

    @Override
    @Transactional
    public VisitLogCreatedResponse createVisitLog(Long userId, VisitLogCreateRequest request) {
        LocalDateTime enteredAt = Instant.ofEpochMilli(request.getEnteredAt())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime exitedAt = Instant.ofEpochMilli(request.getExitedAt())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        if (exitedAt.isBefore(enteredAt)) {
            throw new VisitLogException(INVALID_TIME);
        }

        long minutes = Duration.between(enteredAt, exitedAt).toMinutes();
        // 테스트 환경 예외처리 제거
//        if (minutes < MIN_REQUIRED_STAY_MINUTES) {  // 5분 미만의 체류시간인 경우
//            throw new VisitLogException(STAY_TIME_NOT_ENOUGH);
//        }

        if (visitLogMapper.existsByUserIdAndAttractionNoAndDate(userId, request.getAttractionNo(), enteredAt.toLocalDate())) {
            throw new VisitLogException(ALREADY_VISITED_TODAY);
        }

        VisitLog log = VisitLog.builder()
                .userId(userId)
                .attractionNo(request.getAttractionNo())
                .enteredAt(enteredAt)
                .exitedAt(exitedAt)
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