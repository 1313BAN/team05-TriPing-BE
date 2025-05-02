package com.ssafy.enjoytrip.model.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.enjoytrip.model.dto.ContentTypeDTO;

@Mapper
public interface ContentTypeRepository {
	
	List<ContentTypeDTO> getAll();
}
