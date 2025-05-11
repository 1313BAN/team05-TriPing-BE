package com.ssafy.enjoytrip.domain.attraction.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.domain.attraction.dto.ContentTypeDTO;

@Mapper
public interface ContentTypeMapper {
	List<ContentTypeDTO> getAll();
}
