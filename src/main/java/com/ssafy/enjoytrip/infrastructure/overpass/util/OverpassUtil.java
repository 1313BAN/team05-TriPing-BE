package com.ssafy.enjoytrip.infrastructure.overpass.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.*;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.math.BigDecimal;

@Slf4j
public class OverpassUtil {

    private static final GeometryFactory geometryFactory = new GeometryFactory();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String buildOverpassQuery(String keyword, BigDecimal lat, BigDecimal lng) {
        return "[out:json][timeout:25];" +
                "(" +
                "way[\"name\"=\"" + keyword + "\"](around:1000," + lat + "," + lng + ");" +
                "relation[\"name\"=\"" + keyword + "\"](around:1000," + lat + "," + lng + ");" +
                ");" +
                "out geom;";
    }

    public static BigDecimal haversine(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        final BigDecimal R = BigDecimal.valueOf(6371);
        double dLat = Math.toRadians(lat2.subtract(lat1).doubleValue());
        double dLon = Math.toRadians(lon2.subtract(lon1).doubleValue());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1.doubleValue())) * Math.cos(Math.toRadians(lat2.doubleValue())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R.multiply(BigDecimal.valueOf(c));
    }

    public static String createFallbackCircle(BigDecimal centerLat, BigDecimal centerLon, double radiusMeters, int points) {
        double lat = centerLat.doubleValue();
        double lon = centerLon.doubleValue();

        // 위도 방향: 1도 ≈ 111,000m
        double radiusLatDegrees = radiusMeters / 111_000.0;

        // 경도 방향: 위도에 따라 1도 ≈ 111,320 * cos(lat)
        double radiusLonDegrees = radiusMeters / (111_320.0 * Math.cos(Math.toRadians(lat)));

        Coordinate[] coords = new Coordinate[points + 1];

        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;
            double dx = radiusLonDegrees * Math.cos(angle);
            double dy = radiusLatDegrees * Math.sin(angle);
            coords[i] = new Coordinate(lon + dx, lat + dy);
        }
        coords[points] = coords[0]; // 시작점으로 되돌아와 닫힌 도형 만들기

        LinearRing ring = geometryFactory.createLinearRing(coords);
        Polygon circle = geometryFactory.createPolygon(ring);

        try {
            GeoJSONWriter writer = new GeoJSONWriter();
            GeoJSON geoJson = writer.write(circle);
            return mapper.writeValueAsString(geoJson);
        } catch (Exception e) {
            log.error("Fallback 원형 생성 실패: {}", e.getMessage(), e);
            return null;
        }
    }


    public static Coordinate[] toCoordinateArray(ArrayNode geometry) {
        Coordinate[] coords = new Coordinate[geometry.size()];
        for (int i = 0; i < geometry.size(); i++) {
            JsonNode coord = geometry.get(i);
            BigDecimal lon = BigDecimal.valueOf(coord.get("lon").asDouble());
            BigDecimal lat = BigDecimal.valueOf(coord.get("lat").asDouble());
            coords[i] = new Coordinate(lon.doubleValue(), lat.doubleValue());
        }
        return coords;
    }

    public static JsonNode selectClosestElement(ArrayNode elements, BigDecimal refLat, BigDecimal refLng) {
        JsonNode closest = null;
        BigDecimal minDistance = BigDecimal.valueOf(Double.MAX_VALUE);

        for (JsonNode element : elements) {
            JsonNode bounds = element.path("bounds");
            if (bounds.isMissingNode()) continue;

            BigDecimal centerLat = BigDecimal.valueOf((bounds.get("minlat").asDouble() + bounds.get("maxlat").asDouble()) / 2.0);
            BigDecimal centerLon = BigDecimal.valueOf((bounds.get("minlon").asDouble() + bounds.get("maxlon").asDouble()) / 2.0);
            BigDecimal dist = haversine(refLat, refLng, centerLat, centerLon);

            if (dist.compareTo(minDistance) < 0) {
                minDistance = dist;
                closest = element;
            }
        }

        return closest;
    }
}
