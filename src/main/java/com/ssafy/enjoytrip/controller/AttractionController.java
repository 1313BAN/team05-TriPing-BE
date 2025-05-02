package com.ssafy.enjoytrip.controller;

import com.ssafy.enjoytrip.model.dto.AttractionDTO;
import com.ssafy.enjoytrip.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    /**
     * GET /api/attractions?areaCode=1&sigunguCode=2&contentTypeId=12
     */
    @GetMapping
    public List<AttractionDTO> getAttractions(
            @RequestParam(defaultValue = "0") int areaCode,
            @RequestParam(defaultValue = "0") int sigunguCode,
            @RequestParam(defaultValue = "0") int contentTypeId
    ) {
    	System.out.println("hi");
        return attractionService.getByOption(areaCode, sigunguCode, contentTypeId);
    }

    /**
     * GET /api/attractions/search?keyword=서울타워
     */
    @GetMapping("/search")
    public List<AttractionDTO> searchAttractions(@RequestParam String keyword) {
        return attractionService.getByKeyword(keyword);
    }
}
