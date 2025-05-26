package com.ssafy.enjoytrip.domain.visitlog.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VisitLogsForRecommendDTO {
    private List<VisitLogDTO> visitLogs;
    private int page;
    private int size;
    private long totalCount;
}
