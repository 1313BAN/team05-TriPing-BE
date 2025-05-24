package com.ssafy.enjoytrip.domain.visitlog.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VisitLogCreateRequest {
    private Long attractionNo;
    private LocalDateTime enteredAt;
    private LocalDateTime exitedAt;
    private Boolean preference;
}
