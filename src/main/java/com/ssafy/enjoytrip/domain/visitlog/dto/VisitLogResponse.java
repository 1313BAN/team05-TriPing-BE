package com.ssafy.enjoytrip.domain.visitlog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VisitLogResponse {
    private Long visitLogId;
    private Long attractionNo;
    private String title;
    private Double latitude;
    private Double longitude;
    private String enteredAt;   // LocalDateTime → String
    private String exitedAt;
    private Boolean preference;
}
