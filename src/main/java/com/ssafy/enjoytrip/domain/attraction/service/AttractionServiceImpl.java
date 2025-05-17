package com.ssafy.enjoytrip.domain.attraction.service;

import com.ssafy.enjoytrip.domain.attraction.dto.AttractionPolygonDTO;
import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionPagingDTO;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionMarkerDTO;
import com.ssafy.enjoytrip.domain.attraction.dto.ContentTypeDTO;
import com.ssafy.enjoytrip.domain.attraction.mapper.AttractionMapper;
import com.ssafy.enjoytrip.domain.attraction.mapper.ContentTypeMapper;
import com.ssafy.enjoytrip.infrastructure.overpass.service.OverpassApiService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import static com.ssafy.enjoytrip.util.GeometryUtil.isInsidePolygon;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;
    private final ContentTypeMapper contentTypeMapper;
    private final OverpassApiService overpassApiService;

    private final Map<String, Integer> nameMap = new HashMap<>();

    @PostConstruct
    public void initNameMap() {
        List<Attraction> list = attractionMapper.getAll();
        for (Attraction att : list) {
            nameMap.put(att.getTitle(), att.getNo());
        }
    }

    @Override
    public Attraction getAttraction(int no) {
        return attractionMapper.getById(no);
    }

    @Override
    public AttractionPagingDTO getAttractionsByPage(int page) {
        return attractionMapper.getAllByPage(page);
    }

    @Override
    public AttractionPagingDTO getAttractionsWithContentType(int contentType, int page) {
        return attractionMapper.getWithContentTypeByPage(contentType, page);
    }

    @Override
    public List<ContentTypeDTO> getContentTypes() {
        return contentTypeMapper.getAll();
    }

    @Override
    public List<Attraction> getByOption(int areaCode, int sigunguCode, int contentType) {
        if (contentType == 0) {
            return attractionMapper.getByAreaAndSigungu(areaCode, sigunguCode);
        } else {
            return attractionMapper.getByAreaAndSigunguAndContentType(areaCode, sigunguCode, contentType);
        }
    }

    @Override
    public List<Attraction> getByKeyword(String keyString) {
        Integer id = getMostSimilarValue(nameMap, keyString);
        if (id == null) return Collections.emptyList();
        Attraction res = getAttraction(id);
        return res != null ? List.of(res) : Collections.emptyList();
    }

    private Integer getMostSimilarValue(Map<String, Integer> map, String query) {
        String bestMatchKey = null;
        int maxMatchLength = -1;

        for (String key : map.keySet()) {
            int matchLen = kmpMaxMatchLength(key, query);
            if (matchLen > maxMatchLength) {
                maxMatchLength = matchLen;
                bestMatchKey = key;
            }
        }

        return bestMatchKey != null ? map.get(bestMatchKey) : null;
    }

    private static int kmpMaxMatchLength(String text, String pattern) {
        int[] lps = computeLPSArray(pattern);
        int i = 0, j = 0;
        int maxLen = 0;

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
                maxLen = Math.max(maxLen, j);
                if (j == pattern.length()) j = lps[j - 1];
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return maxLen;
    }

    private static int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0, i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else {
                if (len != 0) len = lps[len - 1];
                else lps[i++] = 0;
            }
        }
        return lps;
    }

    @Override
    public List<AttractionMarkerDTO> getMarkersInViewport(BigDecimal lat1, BigDecimal lat2, BigDecimal lng1, BigDecimal lng2, Integer zoomLevel) {
        if (zoomLevel <= 9) {
            return attractionMapper.findMarkersByArea(lat1, lat2, lng1, lng2, zoomLevel);
        } else if (zoomLevel <= 15) {
            return attractionMapper.findMarkersBySigungu(lat1, lat2, lng1, lng2, zoomLevel);
        } else {
            return attractionMapper.findMarkersAll(lat1, lat2, lng1, lng2, zoomLevel);
        }
    }

    @Override
    public AttractionPolygonDTO checkIfEntered(BigDecimal lat, BigDecimal lng) {
        // 반경 500m 내 rough filtering
        List<Attraction> nearby = attractionMapper.findNearbyAttractions(lat, lng, 500);

        for (Attraction attraction : nearby) {
            // polygon 캐시 조회
            String polygonJson = loadPolygonJson(attraction.getContentId());

            // 캐시 없는 경우 → Overpass API로 조회
            if (polygonJson == null) {
                String rawOverpassJson = overpassApiService.fetchPolygon(attraction.getTitle(), attraction.getLatitude(), attraction.getLongitude());
                System.out.println(rawOverpassJson);

                polygonJson = overpassApiService.normalizeToGeoJson(
                        rawOverpassJson,
                        attraction.getLatitude(),
                        attraction.getLongitude()
                );
                System.out.println(polygonJson);
                if (polygonJson != null) {
                    // Overpass에서 얻어온 값 캐싱
                    // polygonCache.put(attraction.getContentId(), polygonJson);
                } else {
                    continue; // polygon 얻지 못했으면 skip
                }
            }

            if (isInsidePolygon(polygonJson, lat, lng)) {
                return new AttractionPolygonDTO(
                        attraction.getNo(),
                        attraction.getTitle(),
                        attraction.getLatitude(),
                        attraction.getLongitude(),
                        polygonJson
                );
            }
        }

        // 진입한 관광지가 없으면 null 반환
        return null;
    }

    // 임시 polygon 로딩 로직
    private String loadPolygonJson(Integer contentId) {
        // TODO: Redis 캐시 또는 정적 파일/DB 등에서 polygon 로딩
        return null;
    }
}
