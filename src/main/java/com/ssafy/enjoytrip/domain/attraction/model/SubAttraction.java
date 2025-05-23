package com.ssafy.enjoytrip.domain.attraction.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubAttraction {

    private Integer no;           // 하위 명소 코드 (PK)
    private Integer attractionNo; // 상위 명소 코드 (FK)
    private String title;         // 하위 명소 이름
    private BigDecimal latitude;  // 위도
    private BigDecimal longitude; // 경도
}
