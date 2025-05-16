package com.ssafy.enjoytrip.domain.attraction.service;

import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionPagingDTO;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionMarkerDTO;
import com.ssafy.enjoytrip.domain.attraction.dto.ContentTypeDTO;
import com.ssafy.enjoytrip.domain.attraction.mapper.AttractionMapper;
import com.ssafy.enjoytrip.domain.attraction.mapper.ContentTypeMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;
    private final ContentTypeMapper contentTypeMapper;

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

    @Override
    public List<AttractionMarkerDTO> getMarkersInViewport(BigDecimal lat1, BigDecimal lat2, BigDecimal lng1, BigDecimal lng2, Integer zoomLevel) {
        return attractionMapper.findMarkersByViewport(lat1, lat2, lng1, lng2, zoomLevel);
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
}
