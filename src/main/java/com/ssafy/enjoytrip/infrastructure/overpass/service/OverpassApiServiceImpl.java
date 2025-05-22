package com.ssafy.enjoytrip.infrastructure.overpass.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.ssafy.enjoytrip.infrastructure.overpass.util.OverpassUtil.*;
import static com.ssafy.enjoytrip.infrastructure.overpass.util.OverpassGeometryHelper.*;

@Slf4j
@Service
class OverpassApiServiceImpl implements OverpassApiService {

    @Value("${overpass.url}")
    private String OVERPASS_URL;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String fetchPolygon(String keyword, BigDecimal lat, BigDecimal lng) {
        try {
            String query = buildOverpassQuery(keyword, lat, lng);
            String encodedQuery = UriUtils.encode(query, StandardCharsets.UTF_8);
            URL url = new URL(OVERPASS_URL + "?data=" + encodedQuery);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(25000);

            if (conn.getResponseCode() != 200) {
                log.warn("Overpass API 요청 실패 (HTTP {}): {}", conn.getResponseCode(), url);
                return null;
            }

            try (Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                scanner.useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : null;
            }

        } catch (SocketTimeoutException te) {
            log.warn("Overpass API read timeout 발생: keyword='{}'", keyword);
            return null;
        } catch (Exception e) {
            log.error("Overpass API 호출 중 예외 발생: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String normalizeToGeoJson(String overpassJson, BigDecimal refLat, BigDecimal refLng) {
        try {
            ArrayNode elements = null;
            int count = 0;

            if (overpassJson != null) {
                elements = (ArrayNode) mapper.readTree(overpassJson).path("elements");
                count = elements.size();
            }

            // fallback 반경 계산
            double fallbackRadius = (count == 1) ? 50 : (count <= 3) ? 30 : 15;

            // 유효한 요소가 없거나 파싱 실패 시 fallback
            if (elements == null || count == 0) {
                log.warn("Overpass 요소 없음 또는 응답이 null. fallback 원형 생성 실행");
                return createFallbackCircle(refLat, refLng, fallbackRadius, 64);
            }

            JsonNode target = selectClosestElement(elements, refLat, refLng);
            if (target == null) return createFallbackCircle(refLat, refLng, fallbackRadius, 64);

            Geometry resultGeometry = null;
            String type = target.path("type").asText();

            if ("relation".equals(type) && target.has("members")) {
                resultGeometry = createPolygonFromRelationMembers((ArrayNode) target.get("members"));
            } else if ("way".equals(type) && target.has("geometry")) {
                resultGeometry = createPolygonFromWayGeometry((ArrayNode) target.get("geometry"));
            }

            if (resultGeometry == null) return createFallbackCircle(refLat, refLng, fallbackRadius, 64);

            GeoJSON geoJson = new GeoJSONWriter().write(resultGeometry);
            return mapper.writeValueAsString(geoJson);

        } catch (Exception e) {
            log.error("GeoJSON 변환 실패: {}", e.getMessage(), e);
            return createFallbackCircle(refLat, refLng, 50, 64);
        }
    }

}
