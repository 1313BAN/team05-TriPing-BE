package com.ssafy.enjoytrip.domain.visitlog.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VisitLogCreateRequest {
    private Long attractionNo;
    private Long enteredAt;
    private Long exitedAt;
    private Boolean preference;
}
