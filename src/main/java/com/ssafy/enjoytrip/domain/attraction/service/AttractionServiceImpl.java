package com.ssafy.enjoytrip.domain.attraction.service;

import com.ssafy.enjoytrip.domain.attraction.dto.*;
import com.ssafy.enjoytrip.domain.attraction.exception.AttractionException;
import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.mapper.AttractionMapper;
import com.ssafy.enjoytrip.domain.attraction.mapper.ContentTypeMapper;
import com.ssafy.enjoytrip.domain.attraction.model.SubAttraction;
import com.ssafy.enjoytrip.infrastructure.overpass.service.OverpassApiService;
import com.ssafy.enjoytrip.util.RedisKeyUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

import static com.ssafy.enjoytrip.exception.ErrorCode.ATTRACTION_NOT_FOUND;
import static com.ssafy.enjoytrip.util.GeometryUtil.isInsidePolygon;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;
    private final ContentTypeMapper contentTypeMapper;
    private final OverpassApiService overpassApiService;
    private final RedisTemplate<String, String> redisTemplate;
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
        Attraction attraction = attractionMapper.selectByNo(no);
        if (attraction == null) {
            throw new AttractionException(ATTRACTION_NOT_FOUND);
        }
        return attraction;
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
        List<Attraction> nearby = attractionMapper.findNearbyAttractions(lat, lng, 500);

        for (Attraction attraction : nearby) {
            String key = RedisKeyUtil.buildAttractionPolygonKey(attraction.getContentId());
            String polygonJson = redisTemplate.opsForValue().get(key);

            // Redis에 없으면 Overpass에서 가져옴
            if (polygonJson == null) {
                System.out.println("캐싱에 없음: " + attraction.getTitle());
                String rawOverpassJson = overpassApiService.fetchPolygon(
                        attraction.getTitle(),
                        attraction.getLatitude(),
                        attraction.getLongitude()
                );

                polygonJson = overpassApiService.normalizeToGeoJson(
                        rawOverpassJson,
                        attraction.getLatitude(),
                        attraction.getLongitude()
                );

                if (polygonJson != null) {
                    redisTemplate.opsForValue().set(key, polygonJson, Duration.ofDays(14)); // 14일 캐싱
                } else {
                    continue;
                }
            } else {
                System.out.println("캐시 hit: " + attraction.getTitle());
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
        return null;
    }

    @Override
    public List<SubAttractionPolygonDTO> getSubAttractions(int attractionNo) {
        List<SubAttraction> subList = attractionMapper.findSubAttractions(attractionNo);
        List<SubAttractionPolygonDTO> result = new ArrayList<>();

        for (SubAttraction sub : subList) {
            String key = RedisKeyUtil.buildSubAttractionPolygonKey(sub.getNo()); // 예: "sub_polygon:{no}"
            String polygonJson = redisTemplate.opsForValue().get(key);

            if (polygonJson == null) {
                System.out.println("서브 캐싱에 없음: " + sub.getTitle());

                String rawOverpassJson = overpassApiService.fetchPolygon(
                        sub.getTitle(),
                        sub.getLatitude(),
                        sub.getLongitude()
                );

                polygonJson = overpassApiService.normalizeToGeoJson(
                        rawOverpassJson,
                        sub.getLatitude(),
                        sub.getLongitude()
                );

                if (polygonJson != null) {
                    redisTemplate.opsForValue().set(key, polygonJson, Duration.ofDays(14)); // 14일 캐싱
                } else {
                    continue; // 폴리곤이 없는 경우 스킵
                }
            } else {
                System.out.println("서브 캐시 hit: " + sub.getTitle());
            }

            result.add(SubAttractionPolygonDTO.builder()
                    .no(sub.getNo())
                    .title(sub.getTitle())
                    .subPolygonJson(polygonJson)
                    .build());
        }

        return result;
    }
}
