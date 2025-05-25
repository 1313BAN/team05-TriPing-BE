package com.ssafy.enjoytrip.domain.visitlog.dto;

import com.ssafy.enjoytrip.domain.visitlog.model.VisitLog;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
public class VisitLogCreateRequest {
    private Long attractionNo;
    private Long enteredAt;  // epoch millis
    private Long exitedAt;   // epoch millis
    private Boolean preference;

    public VisitLog toEntity() {
        return VisitLog.builder()
                .attractionNo(attractionNo)
                .enteredAt(toLocalDateTime(enteredAt))
                .exitedAt(toLocalDateTime(exitedAt))
                .preference(preference)
                .build();
    }

    private LocalDateTime toLocalDateTime(Long millis) {
        if (millis == null) return null;
        return Instant.ofEpochMilli(millis)
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
    }
}
