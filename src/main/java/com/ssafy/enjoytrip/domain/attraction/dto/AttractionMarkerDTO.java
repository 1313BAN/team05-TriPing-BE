package com.ssafy.enjoytrip.domain.attraction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttractionMarkerDTO {
    private int no;                  // 명소코드 (상세 페이지용 식별자)
    private String title;           // 명소 이름
    private BigDecimal latitude;    // 위도
    private BigDecimal longitude;   // 경도
    private String firstImage1;     // 마커용 이미지
}
