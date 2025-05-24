package com.ssafy.enjoytrip.domain.attraction.mapper;

import com.ssafy.enjoytrip.domain.attraction.dto.AttractionForRecommendDTO;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionMarkerDTO;
import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionPagingDTO;
import com.ssafy.enjoytrip.domain.attraction.model.SubAttraction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface AttractionMapper {

    List<Attraction> getAll();

    AttractionPagingDTO getAllByPage(@Param("page") int page);

    Attraction selectByNo(@Param("no") int no);

    AttractionPagingDTO getWithContentTypeByPage(@Param("contentType") int contentType, @Param("page") int page);

    List<Attraction> getByAreaAndSigunguAndContentType(@Param("areaCode") int areaCode, @Param("sigungu") int sigungu, @Param("contentType") int contentType);

    List<Attraction> getByAreaAndSigungu(@Param("areaCode") int areaCode, @Param("sigungu") int sigungu);

    List<AttractionMarkerDTO> findMarkersByArea(@Param("lat1") BigDecimal lat1,
                                                @Param("lat2") BigDecimal lat2,
                                                @Param("lng1") BigDecimal lng1,
                                                @Param("lng2") BigDecimal lng2,
                                                @Param("zoomLevel") Integer zoomLevel);

    List<AttractionMarkerDTO> findMarkersBySigungu(@Param("lat1") BigDecimal lat1,
                                                   @Param("lat2") BigDecimal lat2,
                                                   @Param("lng1") BigDecimal lng1,
                                                   @Param("lng2") BigDecimal lng2,
                                                   @Param("zoomLevel") Integer zoomLevel);

    List<AttractionMarkerDTO> findMarkersAll(@Param("lat1") BigDecimal lat1,
                                             @Param("lat2") BigDecimal lat2,
                                             @Param("lng1") BigDecimal lng1,
                                             @Param("lng2") BigDecimal lng2,
                                             @Param("zoomLevel") Integer zoomLevel);

    List<Attraction> findNearbyAttractions(
            @Param("lat") BigDecimal lat,
            @Param("lng") BigDecimal lng,
            @Param("radius") int radius
    );

    List<SubAttraction> findSubAttractions(@Param("attractionNo") int attractionNo);

    void increaseVisitCount(@Param("attractionNo") Long attractionNo);

    // AI 추천용 리스트 반환 (사용자 거리 + 반경 기반)
    List<AttractionForRecommendDTO> findAttractionsWithinRadiusForRecommend(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") double radius
    );
}
