package com.ssafy.enjoytrip.domain.attraction.service;

import com.ssafy.enjoytrip.domain.attraction.dto.AttractionMarkerDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestPropertySource("classpath:application.yml") // 또는 application-test.yml
public class AttractionServiceImplTest {

    @Autowired
    private AttractionService attractionService;

    @Test
    void 실제RDS_쿼리_동작테스트() {
        var result = attractionService.getMarkersInViewport(
                new BigDecimal("37.55"),
                new BigDecimal("37.60"),
                new BigDecimal("126.95"),
                new BigDecimal("127.05"),
                13
        );

        assertFalse(result.isEmpty());
    }
}
