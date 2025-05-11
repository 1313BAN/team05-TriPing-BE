package com.ssafy.enjoytrip.domain.attraction.controller;

import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.service.AttractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping
    public List<Attraction> getAttractions(
            @RequestParam(defaultValue = "0") int areaCode,
            @RequestParam(defaultValue = "0") int sigunguCode,
            @RequestParam(defaultValue = "0") int contentTypeId
    ) {
        return attractionService.getByOption(areaCode, sigunguCode, contentTypeId);
    }

    @GetMapping("/search")
    public List<Attraction> searchAttractions(@RequestParam String keyword) {
        return attractionService.getByKeyword(keyword);
    }
}
