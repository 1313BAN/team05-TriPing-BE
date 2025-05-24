package com.ssafy.enjoytrip.domain.visitlog.controller;

import com.ssafy.enjoytrip.auth.jwt.UserPrincipal;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreateRequest;
import com.ssafy.enjoytrip.domain.visitlog.dto.VisitLogCreatedResponse;
import com.ssafy.enjoytrip.domain.visitlog.service.VisitLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/visit-log")
@RequiredArgsConstructor
public class VisitLogController {

    private final VisitLogService visitLogService;

    // 방문기록 생성
    @PostMapping
    public ResponseEntity<VisitLogCreatedResponse> createVisitLog(
            @RequestBody VisitLogCreateRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        VisitLogCreatedResponse res = visitLogService.createVisitLog(userPrincipal.getId(), request);
        URI location = URI.create("/visit-logs/" + res.getId());

        return ResponseEntity
                .created(location)
                .body(res);
    }

    // 방문기록의 선호도 수정
    @PatchMapping("/{visitLogId}/preference")
    public void updatePreference(
            @PathVariable Long visitLogId,
            @RequestParam Integer preference,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        visitLogService.updatePreference(userPrincipal.getId(), visitLogId, preference);
    }
}