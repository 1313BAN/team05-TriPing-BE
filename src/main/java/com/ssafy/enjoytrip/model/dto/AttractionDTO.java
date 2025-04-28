package com.ssafy.enjoytrip.model.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttractionDTO {
    private int no;                  // 명소코드
    private Integer contentId;       // 콘텐츠번호
    private String title;            // 명소이름
    private Integer contentTypeId;   // 콘텐츠타입
    private Integer areaCode;        // 시도코드
    private Integer siGunGuCode;     // 구군코드
    private String firstImage1;      // 이미지경로1
    private String firstImage2;      // 이미지경로2
    private Integer mapLevel;        // 줌레벨
    private BigDecimal latitude;     // 위도
    private BigDecimal longitude;    // 경도
    private String tel;              // 전화번호
    private String addr1;            // 주소1
    private String addr2;            // 주소2
    private String homepage;         // 홈페이지
    private String overview;         // 설명
}