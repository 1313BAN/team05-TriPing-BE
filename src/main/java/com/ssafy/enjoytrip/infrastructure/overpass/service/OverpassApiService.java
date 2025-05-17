package com.ssafy.enjoytrip.infrastructure.overpass.service;

import java.math.BigDecimal;

public interface OverpassApiService {
    String fetchPolygon(String keyword, BigDecimal lat, BigDecimal lng);
    String normalizeToGeoJson(String overpassJson, BigDecimal userLat, BigDecimal userLng);
}
