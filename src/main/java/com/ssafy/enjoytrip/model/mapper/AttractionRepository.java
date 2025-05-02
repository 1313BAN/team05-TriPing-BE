package com.ssafy.enjoytrip.model.mapper;

import com.ssafy.enjoytrip.model.dto.AttractionDTO;
import com.ssafy.enjoytrip.model.dto.AttractionPagingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttractionRepository {

    List<AttractionDTO> getAll();

    AttractionPagingDTO getAllByPage(@Param("page") int page);

    AttractionDTO getById(@Param("id") int id);

    AttractionPagingDTO getWithContentTypeByPage(@Param("contentType") int contentType, @Param("page") int page);

    List<AttractionDTO> getByAreaAndSigunguAndContentType(@Param("areaCode") int areaCode, @Param("sigungu") int sigungu, @Param("contentType") int contentType);

    List<AttractionDTO> getByAreaAndSigungu(@Param("areaCode") int areaCode, @Param("sigungu") int sigungu);
}
