package com.ssafy.enjoytrip.domain.visitlog.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitLog {
    private Long id;
    private Long userId;
    private Long attractionNo;
    private LocalDateTime enteredAt;
    private LocalDateTime exitedAt;
    private Boolean preference;
}
