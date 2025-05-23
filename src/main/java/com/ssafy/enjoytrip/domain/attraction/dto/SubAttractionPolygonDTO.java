package com.ssafy.enjoytrip.domain.attraction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubAttractionPolygonDTO {

    private Integer no;            // 하위 명소 코드
    private String title;          // 하위 명소 이름
    private String subPolygonJson; // 하위 폴리곤 데이터 (GeoJSON 등)
}
