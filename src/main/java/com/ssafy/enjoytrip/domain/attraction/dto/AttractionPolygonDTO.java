package com.ssafy.enjoytrip.domain.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AttractionPolygonDTO {
    // 진입 여부는 200, 204로 판단 가능
    private Integer no;
    private String title;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String polygonJson;
}
