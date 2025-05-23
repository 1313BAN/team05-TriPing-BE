package com.ssafy.enjoytrip.domain.attraction.service;

import com.ssafy.enjoytrip.domain.attraction.dto.*;
import com.ssafy.enjoytrip.domain.attraction.model.Attraction;

import java.math.BigDecimal;
import java.util.List;

public interface AttractionService {
    Attraction getAttraction(int no);
    AttractionPagingDTO getAttractionsByPage(int page);
    AttractionPagingDTO getAttractionsWithContentType(int contentType, int page);
    List<ContentTypeDTO> getContentTypes();
    List<Attraction> getByOption(int areaCode, int sigunguCode, int contentType);
    List<Attraction> getByKeyword(String keyString);
    List<AttractionMarkerDTO> getMarkersInViewport(BigDecimal lat1, BigDecimal lat2, BigDecimal lng1, BigDecimal lng2, Integer zoomLevel);
    AttractionPolygonDTO checkIfEntered(BigDecimal lat, BigDecimal lng);
    List<SubAttractionPolygonDTO> getSubAttractions(int attractionNo);

}
