package com.ssafy.enjoytrip.domain.attraction.mapper;

import com.ssafy.enjoytrip.domain.attraction.model.Attraction;
import com.ssafy.enjoytrip.domain.attraction.dto.AttractionPagingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttractionMapper {

    List<Attraction> getAll();

    AttractionPagingDTO getAllByPage(@Param("page") int page);

    Attraction getById(@Param("id") int id);

    AttractionPagingDTO getWithContentTypeByPage(@Param("contentType") int contentType, @Param("page") int page);

    List<Attraction> getByAreaAndSigunguAndContentType(@Param("areaCode") int areaCode, @Param("sigungu") int sigungu, @Param("contentType") int contentType);

    List<Attraction> getByAreaAndSigungu(@Param("areaCode") int areaCode, @Param("sigungu") int sigungu);
}
