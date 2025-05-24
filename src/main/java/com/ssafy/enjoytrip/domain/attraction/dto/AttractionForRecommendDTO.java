package com.ssafy.enjoytrip.domain.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttractionForRecommendDTO {
    private Long no;
    private String title;
    private Integer visitCount;
    private Double distance; // 미터 단위
}
