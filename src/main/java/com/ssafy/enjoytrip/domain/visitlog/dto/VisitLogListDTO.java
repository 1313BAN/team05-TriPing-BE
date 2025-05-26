package com.ssafy.enjoytrip.domain.visitlog.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VisitLogListDTO {
    private List<VisitLogDTO> visitLogs;
    private int page;
    private long totalCount;
}
