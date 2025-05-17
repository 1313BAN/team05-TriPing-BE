package com.ssafy.enjoytrip.domain.attraction.controller;

import com.ssafy.enjoytrip.domain.attraction.dto.AttractionPolygonDTO;
import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionMarkerDTO;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    // 조건 검색
    @GetMapping
    public List<Attraction> getAttractions(
            @RequestParam(defaultValue = "0") int areaCode,
            @RequestParam(defaultValue = "0") int sigunguCode,
            @RequestParam(defaultValue = "0") int contentTypeId
    ) {
        return attractionService.getByOption(areaCode, sigunguCode, contentTypeId);
    }

    // 키워드 검색
    @GetMapping("/search")
    public List<Attraction> searchAttractions(@RequestParam String keyword) {
        return attractionService.getByKeyword(keyword);
    }

    // 뷰포트 기반 마커 조회
    @GetMapping("/markers")
    public List<AttractionMarkerDTO> getMarkersInViewport(
            @RequestParam BigDecimal lat1,
            @RequestParam BigDecimal lat2,
            @RequestParam BigDecimal lng1,
            @RequestParam BigDecimal lng2,
            @RequestParam Integer zoomLevel
    ) {
        return attractionService.getMarkersInViewport(lat1, lat2, lng1, lng2, zoomLevel);
    }

    // 위치 기반 진입 여부 확인
    @GetMapping("/entered")
    public ResponseEntity<AttractionPolygonDTO> checkIfEntered(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lng
    ) {
        AttractionPolygonDTO dto = attractionService.checkIfEntered(lat, lng);
        if (dto == null) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(dto); // 200 + body
    }
}
