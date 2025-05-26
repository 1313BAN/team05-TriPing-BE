package com.ssafy.enjoytrip.domain.visitlog.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VisitLogDTO {
    private Long visitLogId;
    private Long attractionNo;
    private String title;
    private Double latitude;
    private Double longitude;
    private LocalDateTime enteredAt;   // LocalDateTime → String
    private LocalDateTime exitedAt;
    private Integer preference;
}
