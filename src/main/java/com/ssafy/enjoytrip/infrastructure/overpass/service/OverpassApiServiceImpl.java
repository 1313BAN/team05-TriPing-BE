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
            ArrayNode elements = (ArrayNode) mapper.readTree(overpassJson).path("elements");
            int count = elements.size();

            // 응답 수에 따라 fallback 반경 결정
            double fallbackRadius = (count <= 1) ? 100 : (count <= 7) ? 50 : 30;
            if (count == 0) return createFallbackCircle(refLat, refLng, fallbackRadius, 32);

            // 중심점 기준 가장 가까운 요소 선택
            JsonNode target = selectClosestElement(elements, refLat, refLng);
            if (target == null) return createFallbackCircle(refLat, refLng, fallbackRadius, 32);

            Geometry resultGeometry = null;
            String type = target.path("type").asText();

            // relation 타입 처리
            if ("relation".equals(type) && target.has("members")) {
                resultGeometry = createPolygonFromRelationMembers((ArrayNode) target.get("members"));

                // way 타입 처리
            } else if ("way".equals(type) && target.has("geometry")) {
                resultGeometry = createPolygonFromWayGeometry((ArrayNode) target.get("geometry"));
            }

            if (resultGeometry == null) return createFallbackCircle(refLat, refLng, fallbackRadius, 32);

            GeoJSON geoJson = new GeoJSONWriter().write(resultGeometry);
            String jsonString = mapper.writeValueAsString(geoJson);
            log.debug("GeoJSON 변환 결과: {}", jsonString);
            return jsonString;

        } catch (Exception e) {
            log.error("GeoJSON 변환 실패: {}", e.getMessage(), e);
            return createFallbackCircle(refLat, refLng, 50, 32);
        }
    }
}
