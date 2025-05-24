package com.ssafy.enjoytrip.domain.visitlog.mapper;

import com.ssafy.enjoytrip.domain.visitlog.model.VisitLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VisitLogMapper {
    void insertVisitLog(VisitLog log);
    void updatePreference(@Param("userId") Long userId,
                          @Param("visitLogId") Long visitLogId,
                          @Param("preference") Integer preference);
    boolean existsByIdAndUserId(@Param("visitLogId") Long visitLogId, @Param("userId") Long userId);
}