package com.ssafy.enjoytrip.util;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.wololo.jts2geojson.GeoJSONReader;

import java.math.BigDecimal;

@Slf4j
public class GeometryUtil {

    private static final GeometryFactory factory = new GeometryFactory();

    public static boolean isInsidePolygon(String polygonJson, BigDecimal lat, BigDecimal lng) {
        try {
            Point point = factory.createPoint(new Coordinate(lng.doubleValue(), lat.doubleValue()));
            GeoJSONReader reader = new GeoJSONReader();
            Geometry geometry = reader.read(polygonJson);
            return geometry.contains(point);
        } catch (Exception e) {
            log.warn("GeoJSON 내부 포함 판단 실패: {}", e.getMessage(), e);
            return false;
        }
    }
}
