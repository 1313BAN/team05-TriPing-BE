package com.ssafy.enjoytrip.domain.visitlog.mapper;

import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogDTO;
import com.ssafy.enjoytrip.domain.visitlog.model.VisitLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface VisitLogMapper {
    void insertVisitLog(VisitLog log);
    void updatePreference(@Param("userId") Long userId,
                          @Param("visitLogId") Long visitLogId,
                          @Param("preference") Integer preference);
    boolean existsByIdAndUserId(@Param("visitLogId") Long visitLogId, @Param("userId") Long userId);
    boolean existsByUserIdAndAttractionNoAndDate(
            @Param("userId") Long userId,
            @Param("attractionNo") Long attractionNo,
            @Param("date") LocalDate date
    );

    List<VisitLogDTO> findVisitLogsByUser(
            @Param("userId") Long userId,
            @Param("size") Integer size,
            @Param("offset") Integer offset
    );

    long countVisitLogsByUser(@Param("userId") Long userId);

}