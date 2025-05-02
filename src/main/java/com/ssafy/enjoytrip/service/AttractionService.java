package com.ssafy.enjoytrip.service;

import com.ssafy.enjoytrip.model.dto.AttractionDTO;
import com.ssafy.enjoytrip.model.dto.AttractionPagingDTO;
import com.ssafy.enjoytrip.model.dto.ContentTypeDTO;
import com.ssafy.enjoytrip.model.mapper.AttractionRepository;
import com.ssafy.enjoytrip.model.mapper.ContentTypeRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private final AttractionRepository attractionRepository;
    private final ContentTypeRepository contentTypeRepository;

    private final Map<String, Integer> nameMap = new HashMap<>();

    @PostConstruct
    public void initNameMap() {
        List<AttractionDTO> list = attractionRepository.getAll();
        for (AttractionDTO att : list) {
            nameMap.put(att.getTitle(), att.getNo());
        }
    }
    
    public AttractionDTO getAttraction(int no) {
        return attractionRepository.getById(no);
    }

    public AttractionPagingDTO getAttractionsByPage(int page) {
        return attractionRepository.getAllByPage(page);
    }

    public AttractionPagingDTO getAttractionsWithContentType(int contentType, int page) {
        return attractionRepository.getWithContentTypeByPage(contentType, page);
    }

    public List<ContentTypeDTO> getContentTypes() {
        return contentTypeRepository.getAll();
    }

    public List<AttractionDTO> getByOption(int areaCode, int sigunguCode, int contentType) {
        if (contentType == 0) {
            return attractionRepository.getByAreaAndSigungu(areaCode, sigunguCode);
        } else {
            return attractionRepository.getByAreaAndSigunguAndContentType(areaCode, sigunguCode, contentType);
        }
    }

    public List<AttractionDTO> getByKeyword(String keyString) {
        Integer id = getMostSimilarValue(nameMap, keyString);
        if (id == null) return Collections.emptyList();
        AttractionDTO res = getAttraction(id);
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
}