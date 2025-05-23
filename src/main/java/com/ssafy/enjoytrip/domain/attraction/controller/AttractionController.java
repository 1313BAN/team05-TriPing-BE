package com.ssafy.enjoytrip.domain.attraction.controller;

import com.ssafy.enjoytrip.domain.attraction.dto.AttractionPolygonDTO;
import com.ssafy.enjoytrip.domain.attraction.dto.SubAttractionPolygonDTO;
import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionMarkerDTO;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import com.ssafy.enjoytrip.infrastructure.gpt.dto.GptGuideResponse;
import com.ssafy.enjoytrip.infrastructure.gpt.service.GptService;
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
    private final GptService gptService;


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
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dto);
    }

    // 상세 조회
    @GetMapping("/{no}")
    public ResponseEntity<Attraction> getAttractionByNo(@PathVariable int no) {
        Attraction attraction = attractionService.getAttraction(no);
        return ResponseEntity.ok(attraction);
    }

    // 하위 명소 리스트 조회
    @GetMapping("/{no}/subs")
    public ResponseEntity<List<SubAttractionPolygonDTO>> getSubAttractions(@PathVariable("no") int attractionNo) {
        return ResponseEntity.ok(attractionService.getSubAttractions(attractionNo));
    }

    //  GPT 기반 가이드 정보 제공
    @GetMapping("/guide/{id}")
    public ResponseEntity<GptGuideResponse> getAttractionGuide(@PathVariable("id") int id) {
        // 1️⃣ ID 기반으로 관광지 정보 조회
        Attraction attraction = attractionService.getAttraction(id);

        // 2️⃣ GPT에서 JSON 응답 받아서 DTO로 매핑됨
        GptGuideResponse guideResponse = gptService.getGuideByTitleAndAddress(
                attraction.getTitle(),
                attraction.getAddr1()
        );

        // 3️⃣ 응답 DTO로 감싸서 프론트로 반환
        return ResponseEntity.ok(guideResponse);
    }


}
